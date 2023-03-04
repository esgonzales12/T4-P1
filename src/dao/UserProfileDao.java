package dao;

import dao.SecurityDao;
import dao.domain.LogRecord;
import dao.domain.UserProfile;
import administrator.Authorizer;
import administrator.AdminController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserProfileDao {
    private static Map<String, User> userMap;
    private static User rootUser;
    private static Authorizer authorizer;
    private AdminController adminController;
    private SecurityDao securityDao;

    public UserProfileDao(Authorizer authorizer, AdminController adminController, SecurityDao securityDao) {
        userMap = new HashMap<>();
        rootUser = new User(UUID.randomUUID().toString(), "root");
        userMap.put(rootUser.getUsername(), rootUser);
        this.authorizer = authorizer;
        this.adminController = adminController;
        this.securityDao = securityDao;
    }
    

    public static User save(User user) {
        if (authorizer.authorizeSave(user)) {
            userMap.put(user.getUsername(), user);
            return user;
        }
        return null;
    }

    public static User delete(String username) {
        if (!authorizer.authorizeDelete(username)) {
            return null;
        }
        if (username.equals(rootUser.getUsername())) {
            return null;
        }
        return userMap.remove(username);
    }

    public User getUser(String username) {
        if (!authorizer.authorizeGetUser(username)) {
            return null;
        }
        return userMap.get(username);
    }

    public User getRoot() {
        return rootUser;
    }

    public void authorizeUser(User user) {
        adminController.authorizeUser(user);
    }

    public void deauthorizeUser(User user) {
        adminController.deauthorizeUser(user);
    }


    public void saveUserProfile(UserProfile userProfile) {
        User user = new User(userProfile.getUsername(), userProfile.getPassword());
        UserProfileDao.save(user);
    }
    
    public User deleteUserProfileByUsername(String username) {
        return UserProfileDao.delete(username);
    }
    
    public void updateUserProfile(User user) {
        UserProfileDao.save(user);
    }
    
}
