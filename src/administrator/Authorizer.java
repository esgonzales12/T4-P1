package administrator;

import dao.SecurityDao;
import dao.UserProfileDao;
import dao.domain.LogRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Authorizer {

    private final SecurityDao securityDao;
    private final UserProfileDao userProfileDao;

    public Authorizer(SecurityDao adminUsername, UserProfileDao adminPassword) {
        this.securityDao = adminUsername;
        this.userProfileDao = adminPassword;
    }

//    public boolean isAuthorized(String username, String password, Operation authority) {
//        // Verify that the username and password are correct
//        User userProfile = userProfileDao.deleteUserProfileByUsername(username);
//        if (userProfile == null || !userProfile.getPassword().equals(securityDao.stringHash(password))) {
//            return false;
//        }
//        // Determine if the user has sufficient privileges to perform the specified operation
//        return userProfile.getAuthorities().compareTo(authority) >= 0;
//    }
//
//    public boolean authorizeSave(User user) {
//        // Check if the user has authorization to save a user profile
//        return user.getAuthorities().contains(Operation.USER_MANAGEMENT);
//    }
//
//    public boolean authorizeDelete(String username) {
//        // Check if the user has authorization to delete a user profile
//        User userProfile = userProfileDao.deleteUserProfileByUsername(username);
//        return userProfile != null && userProfile.getAuthorities().contains(Operation.USER_MANAGEMENT);
//    }
//
//    public boolean authorizeGetUser(String username) {
//        // Check if the user has authorization to retrieve logs for a specific user
//        User userProfile = userProfileDao.deleteUserProfileByUsername(username);
//        return userProfile != null && userProfile.getAuthorities().contains(Operation.USER_MANAGEMENT);
//    }
//
//
//    public void deauthorizeUser(User user) {
//        // Revoke a user's authorization
//        user.getAuthorities().clear();
//    }

    public void logAction(String username, String action) {
        // Logging to persistent storage
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        LogRecord logRecord = new LogRecord.Builder()
                .setUsername(username)
                .setOperation(action)
                .setDateTime(formattedDateTime)
                .build();
        securityDao.saveLogRecord(logRecord);
    }

    public List<LogRecord> getLogs(String username) {
        return securityDao.getLogRecordsByUsername(username);
    }
}
