package example;

import administrator.AdministratorImpl;
import dao.domain.Authority;
import dao.domain.LogRecord;
import dao.domain.UserProfile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserProfileDaoEx {
    private static final String USER_PROFILE_PATH = "src/dao/data/userProfiles.txt";

    public static void main(String[] args) throws Exception {
        AdministratorImpl administrator = new AdministratorImpl();
        String username = "1234";
        String pw = "AEF545FB";
        Authority authority = Authority.ROOT;
        UserProfile userProfile = new UserProfile(administrator.encrypt(username),
                administrator.encrypt(pw),
                administrator.encrypt(authority.getValue()));
        try (Writer writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(USER_PROFILE_PATH, true),
                                     StandardCharsets.UTF_8))) {
            writer.write(userProfile + "\n");
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        System.exit(0);
        // how to find the profile that is stored
        try (BufferedReader reader
                     = new BufferedReader(
                new FileReader(USER_PROFILE_PATH))){
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String [] temp = currentLine.split(",");
                String unm = temp[0];
                if (unm.equals(userProfile.getUsername())) {
                    // we found the user profile
                }
            }
        } catch (IOException e) {
            // return false if error
        }
        // otherwise return true

    }
}
