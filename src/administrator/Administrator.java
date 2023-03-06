package administrator;

import dao.domain.Authority;
import dao.domain.LogRecord;
import dao.domain.Operation;
import dao.domain.UserProfile;

import java.util.List;

public interface Administrator {

    void saveUser(String username, String password, Authority authority);
    void deleteUser(String username);
    void authorizeUser(UserProfile user, Operation operation);
    List<LogRecord> getLogs();
}
