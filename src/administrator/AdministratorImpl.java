package administrator;

import dao.SecurityDao;
import dao.UserProfileDao;
import dao.domain.*;
import log.StaticLogBase;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static dao.domain.Operation.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class AdministratorImpl  extends StaticLogBase implements Administrator {

    private static final String ALGORITHM = "AES";
    private static final Map<Authority, List<Operation>> ALLOWED_OPERATIONS = Map.ofEntries(
                    Map.entry(Authority.ROOT, List.of(USER_MANAGEMENT, CHANGE_PASSWORD, EXPORT_LOGS, BASE_ACCESS)),
                    Map.entry(Authority.USER, List.of(CHANGE_PASSWORD, BASE_ACCESS)));
    private final UserProfileDao userProfileDao;
    private final SecurityDao securityDao;
    private UserProfile userPrincipal;
    private String encryptionKey;

    public AdministratorImpl() {
        userProfileDao = new UserProfileDao();
        securityDao = new SecurityDao();
        encryptionKey = securityDao.getEncryptionKey();
        userPrincipal = null;
    }


    @Override
    public boolean saveUser(String username, String password, Authority authority) {
        try {
            if (isNull(encryptionKey)) encryptionKey = securityDao.getEncryptionKey();
            UserProfile existing = userProfileDao.getUser(requireNonNull(encrypt(username)));

            if (isNull(password)) {
                log.info("PW REQUIRED FOR CREATE OR UPDATE");
                return false;
            }

            // creating a new user
            else if (isNull(existing)) {
                int currentUsers = userProfileDao.countNumber();
                if (currentUsers >= 10) {
                    log.info("UNABLE TO CREATE MORE THAN 10 USER PROFILES");
                    return false;
                }
                UserProfile userProfile = new UserProfile(
                        requireNonNull(encrypt(username)),
                        requireNonNull(hash(password)),
                        requireNonNull(encrypt(authority.getValue())));
                return userProfileDao.save(userProfile);
            }

            // changing password
            log.info("CHANGING USER PASSWORD");

            return userProfileDao.save(new UserProfile(
                    existing.getUsername(),
                    requireNonNull(hash(password)),
                    existing.getAuthorities()));

        } catch (NullPointerException nullPointerException) {
            // handles null cases for encrypt, decrypt, hash
            log.warning("NULL POINTER IN SAVE");
            log.warning(nullPointerException.getMessage());
        } catch (Exception e) {
            log.warning("UNEXPECTED EXCEPTION ON SAVE");
            log.warning(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean deleteUser(String username) {
        try {
            if (isNull(encryptionKey)) encryptionKey = securityDao.getEncryptionKey();

            String rootAuthority = requireNonNull(encrypt(Authority.ROOT.getValue()));
            UserProfile root = userProfileDao.getRoot(rootAuthority);
            String rootUsername = requireNonNull(decrypt(root.getUsername()));
            if  (rootUsername.equals(username)) {
                log.info("UNABLE TO DECRYPT OR ATTEMPTED TO DELETE ROOT USER");
                return false;
            }
            return userProfileDao.delete(username);
        } catch (NullPointerException nullPointerException) {
            // handles null cases for encrypt, decrypt, hash
            log.warning("NULL POINTER IN AUTHORIZE");
            log.warning(nullPointerException.getMessage());
        } catch (Exception e) {
            log.warning("UNEXPECTED EXCEPTION ON DELETE");
            log.warning(e.getMessage());
        }
        return false;
    }

    @Override
    public Authorization authorizeUser(UserProfile user, Operation operation) {
        try {
            if (isNull(encryptionKey)) encryptionKey = securityDao.getEncryptionKey();

            if (isNull(user.getUsername()) || isNull(user.getPassword())) {
                log.info("USERNAME AND PASSWORD REQUIRED FOR AUTHORIZE");
                return Authorization.UNAUTHORIZED;
            }

            String encryptedUsername = requireNonNull(encrypt(user.getUsername()));
            UserProfile storedUser = userProfileDao.getUser(encryptedUsername);

            if (isNull(storedUser)) {
                log.info("NO USER FOUND WITH USERNAME: " + user.getUsername());
                return Authorization.UNAUTHORIZED;
            }

            String givenPassword = requireNonNull(hash(user.getPassword()));
            String requiredPassword = storedUser.getPassword();

            if (!givenPassword.equals(requiredPassword)) {
                log.info("INVALID PASSWORD FOR USER: " + user.getUsername());
                return Authorization.UNAUTHORIZED;
            }

            String userAuthority = requireNonNull(decrypt(storedUser.getAuthorities()));
            Authority authority = null;
            for (Authority auth: Authority.values()) {
                if (userAuthority.equals(auth.getValue())) {
                    authority = auth;
                    break;
                }
            }

            if (isNull(authority) || !ALLOWED_OPERATIONS.get(authority).contains(operation)) {
                log.info(format("USER %s WITH AUTHORITY %s CANNOT PERFORM OPERATION %s", user.getUsername(), authority, operation));
                return Authorization.UNAUTHORIZED;
            }

            boolean logged = securityDao.save(new LogRecord.Builder()
                    .setUsername(storedUser.getUsername())
                    .setOperation(requireNonNull(encrypt(operation.toString())))
                    .setDateTime(requireNonNull(encrypt(String.valueOf(Instant.now()))))
                    .build());

            if (logged) {
                userPrincipal = user;
                return Authorization.AUTHORIZED;
            }

            log.warning(format("UNABLE TO LOG AUTHORIZATION: %s FOR OPERATION %s", user.getUsername(), operation));
            return Authorization.UNAUTHORIZED;

        } catch (NullPointerException nullPointerException) {
            log.warning("NULL POINTER IN AUTHORIZE");
            log.warning(nullPointerException.getMessage());
        } catch (Exception e) {
            log.warning("UNEXPECTED EXCEPTION ON AUTHORIZE");
            log.warning(e.getMessage());
        }
        return Authorization.UNAUTHORIZED;
    }

    @Override
    public List<LogRecord> getLogs() {
        List<LogRecord> result = new ArrayList<>();
        List<LogRecord> encryptedRecords = securityDao.getRecords();
        try {
            if (isNull(encryptionKey)) encryptionKey = securityDao.getEncryptionKey();

            for (LogRecord record: encryptedRecords) {
                result.add(new LogRecord.Builder()
                        .setUsername(requireNonNull(decrypt(record.getUsername())))
                        .setDateTime(requireNonNull(decrypt(record.getDateTime())))
                        .setOperation(requireNonNull(decrypt(record.getOperation())))
                        .build());
            }
        } catch (NullPointerException nullPointerException) {
            log.warning("NULL POINTER IN GET LOGS");
            log.warning(nullPointerException.getMessage());
        } catch (Exception e) {
            log.warning("UNEXPECTED EXCEPTION IN GET LOGS");
            log.warning(e.getMessage());
        }
        return result;
    }

    @Override
    public UserProfile getUserPrincipal() {
        return userPrincipal;
    }

    public String hash(String input) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            log.warning("NO SHA-256 ALGORITHM FOUND");
            log.warning(e.getMessage());
            return null;
        }
        return hexString.toString();
    }

    public String encrypt(String value) {
        byte[] encryptedValue;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            encryptedValue = cipher.doFinal(value.getBytes());
        } catch (Exception e) {
            log.warning("UNABLE TO ENCRYPT");
            log.info(e.getMessage());
            return null;
        }
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decrypt(String value) {
        byte[] decryptedValue;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(value);
            decryptedValue = cipher.doFinal(decodedValue);
        } catch (Exception e) {
            log.warning("UNABLE TO DECRYPT");
            log.info(e.getMessage());
            return null;
        }
        return new String(decryptedValue);
    }

}
