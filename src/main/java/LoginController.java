import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by Iulia-PC on 5/30/2016.
 */
@ManagedBean
@SessionScoped
public class LoginController implements Serializable{

    private User user;
    private String host = "fenrir.info.uaic.ro";
    private int port = 22;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String validateUsername(String username, String pass){


        return null;
    }
}
