package dao.domain;

import java.util.List;

public class UserProfile {
    private String username;
    private String password;
    private String authority;

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

    public String getAuthorities() {
        return authority;
    }

    @Override
    public String toString() {
        return username + "," + password + ","  + authority;
    }
}