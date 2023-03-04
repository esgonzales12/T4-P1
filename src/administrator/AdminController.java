package administrator;

import dao.User;
import dao.UserProfileDao;
import dao.domain.Authorities;
import dao.domain.UserProfile;

public class AdminController {

    private final UserProfileDao userProfileDao;

    public AdminController(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public void saveUser(String username, String password, Authorities authority) {
        UserProfile userProfile = new UserProfile(username, password, authority);
        userProfileDao.saveUserProfile(userProfile);
    }

    public void deleteUser(String username) {
        userProfileDao.deleteUserProfileByUsername(username);
    }
    
    public void authorizeUser(User user) {
        userProfileDao.updateUserProfile(user);
    }
    
    public void deauthorizeUser(User user) {
        Authorities authorities = Authorities.CANCEL;
        user.setAuthorities(authorities);
        userProfileDao.updateUserProfile(user);
        
        user.setAuthorities(authorities);
        userProfileDao.updateUserProfile(user);
    }
    
}
