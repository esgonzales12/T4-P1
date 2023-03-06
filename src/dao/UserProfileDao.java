package dao;

import dao.domain.UserProfile;
import log.StaticLogBase;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserProfileDao extends StaticLogBase {
    private static final String USER_PROFILE_PATH = "src/dao/data/userProfiles.txt";
    public UserProfileDao() {
    }
    
    public int countNumber() {
        int count = 0;
        try (BufferedReader reader
                     = new BufferedReader(
                             new FileReader(USER_PROFILE_PATH))){
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            log.severe("ERROR READING USER PROFILES");
            log.severe(e.getMessage());
        }
        return count;
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
        try (BufferedReader reader
                     = new BufferedReader(
                             new FileReader(USER_PROFILE_PATH))){
            String currentLine;
            StringBuilder fileContent = new StringBuilder();
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] temp = currentLine.split(",");
                String unm = temp[0];
                if (unm.equals(username)) {
                    found = true;
                    continue;
                }
                fileContent.append(currentLine).append("\\n");
            }
            if (!found) {
                return false;
            }
            try (Writer writer =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new FileOutputStream(USER_PROFILE_PATH),
                                         StandardCharsets.UTF_8))) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
            log.severe("ERROR DELETING USER PROFILE");
            log.severe(e.getMessage());
            return false;
        }
        return true;
    }

    public UserProfile getUser(String username) {
        // Looks for the username in the text file then builds it (User object) if exists
        try (BufferedReader reader
                     = new BufferedReader(
                             new FileReader(USER_PROFILE_PATH))){
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] temp = currentLine.split(",");
                String unm = temp[0];
                if (unm.equals(username)) {
                    String pass = temp[1];
                    String auth = temp[2];
                    return new UserProfile(unm, pass, auth);
                }
            }
        } catch (IOException e) {
            log.severe("ERROR READING USER PROFILE");
            log.severe(e.getMessage());
        }
        return null;
    }

    public UserProfile getRoot() {
        return getUser("root");
    }

}
