package administrator;

import dao.User;
import dao.domain.Authorities;
import dao.domain.LogRecord;
import dao.domain.Operation;
import dao.domain.UserProfile;

import java.util.List;

public interface Administrator {

    void saveUser(String username, String password, Authorities authority);
    void deleteUser(String username);
    void authorizeUser(User user, Operation operation);
    List<LogRecord> getLogs();
}
