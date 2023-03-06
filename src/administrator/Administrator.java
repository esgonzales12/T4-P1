package administrator;

import dao.domain.*;

import java.util.List;

public interface Administrator {

    boolean saveUser(String username, String password, Authority authority);
    boolean deleteUser(String username);
    Authorization authorizeUser(UserProfile user, Operation operation);
    List<LogRecord> getLogs();
}
