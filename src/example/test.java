package example;

import administrator.AdministratorImpl;
import dao.domain.UserProfile;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class test {
    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        UserProfile userProfile = new UserProfile(null, null, null);
//        System.out.println(String.format("%s not allowed"));
        AdministratorImpl administrator = new AdministratorImpl();
        for(int i = 4; i < 13; i++) {
            for (int count = 0; count < 100; count++) {
                String value = randomAlphaNumeric(i);
                String encrypted = requireNonNull(administrator.encrypt(value));
                String decrypted = requireNonNull(administrator.decrypt(encrypted));
                if (!decrypted.equals(value)) System.out.println("ERROR");
            }
        }
    }

    public static String randomAlphaNumeric(int len) {
        StringBuilder sb = new StringBuilder();
        RANDOM.setSeed(System.nanoTime());
        for (int i = 0; i < len; i++) {
            sb.append(ALPHA_NUM.charAt(RANDOM.nextInt(ALPHA_NUM.length())));
        }
        return sb.toString();
    }
}
