package login;

/**
 * Created by Iulia-PC on 5/30/2016.
 */
public class User {
    private String username;
    private String password;
    private String isAdmin;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }
}
