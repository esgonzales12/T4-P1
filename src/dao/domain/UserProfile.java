package dao.domain;

public class UserProfile {
    private String username;
    private String password;
    private String authorities;

    public UserProfile(String username, String password, String string) {
        this.username = username;
        this.password = password;
        this.authorities = string;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorities() {
        return authorities;
    }
}