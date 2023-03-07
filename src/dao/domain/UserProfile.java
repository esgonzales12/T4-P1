package dao.domain;

public class UserProfile {
    private final String username;
    private final String password;
    private final String authority;

    public UserProfile(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return username + "," + password + ","  + authority;
    }
}