package dao.domain;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, authority);
    }

    @Override
    public String toString() {
        return username + "," + password + ","  + authority;
    }
}