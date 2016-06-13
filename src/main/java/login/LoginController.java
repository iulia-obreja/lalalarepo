package login;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/12/2016.
 */
@ManagedBean(name = "loginCtrl", eager = true)
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean loggedIn;


    public String verifyUser() {

        if (username == null || password == null) {
            return "#";
        }

        String response = LoginDAO.validate(username, password);

        if(response.equals(INVALID)){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return NavigationController.goToLoginAgain();
        }else{
            loggedIn = true;
            if (response.equals(ADMIN)) {
                return NavigationController.goToAdmin();
            }
            return NavigationController.goToStudent();
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("apel");
        NavigationController.goToLoginAgain();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
