package dao;

import dao.domain.UserProfile;
import log.StaticLogBase;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserProfileDao extends StaticLogBase {
    private static final String USER_PROFILE_PATH = "src/dao/data/userProfiles.txt";
    public UserProfileDao() {
    }
    

    public boolean save(UserProfile user) {
        // Write to the userProfiles.txt file
        // 1. Open the txt file
        // 2. Search for the user (if they exist)
        // 3. If exists update, and write it back to the file
        try (Writer writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(USER_PROFILE_PATH, true),
                                     StandardCharsets.UTF_8))) {
            writer.write(user.toString() + "\n");
        } catch (IOException e) {
            log.severe("ERROR SAVING LOG RECORD");
            log.severe(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(String username) {
        // Need to verify the user is not the root user
        // Remove a line from the text file, if we can do that return true
//        if (!authorizer.authorizeDelete(username)) {
////            return null;
//        }
//        if (username.equals(rootUser.getUsername())) {
////            return null;
//        }
//        return userMap.remove(username);/
        return false;
    }

    public UserProfile getUser(String username) {
        // Looks for the username in the text file then builds it (User object) if exists
//        if (!authorizer.authorizeGetUser(username)) {
//            return null;
//        }
//        return userMap.get(username);
        return null;
    }

    public UserProfile getRoot() {
        return null;
    }

}
