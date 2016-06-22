package login;

import org.primefaces.context.RequestContext;

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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private String authCode;
    private String authCodeFromUser;
    private boolean validUser;
    private String userType;
    private static List<String> userData;


    public LoginController(){
        validUser = false;
    }

    public String loginAsAdmin() {

        if (username == null || password == null) {
            return "#";
        }

        String response = LoginDAO.validate(username, password);

        if (response.equals(INVALID)) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logare nereusita", "Username sau parola incorecta");

            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return NavigationController.goToLoginAgain();
        } else {
            loggedIn = true;
            if (response.equals(ADMIN)) {
                userType = ADMIN;
                return NavigationController.goToAdmin();
            }
            userType = STUDENT;
            return NavigationController.goToStudent();
        }
    }

    public String logout() {
        loggedIn = false;
        return NavigationController.goToLoginAgain();
    }

    public void checkUser(){
        userData = LoginDAO.checkForUserInDB(username);

        if (userData.size() == 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eroare", "Email-ul introdus nu se afla in baza de date");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }else{
            validUser = true;
            authCode = generateAuthenticationCode();
            SendEmailService.sendEmail(username + "@info.uaic.ro", authCode);
        }
    }

    public String loginAsStudent() {
        if(authCode.equalsIgnoreCase(authCodeFromUser)){
            loggedIn = true;
            userType = STUDENT;
            return NavigationController.goToStudent();
        }
        else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eroare", "Email-ul introdus nu se afla in baza de date");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return NavigationController.goToLoginAgain();
        }
    }

    private String generateAuthenticationCode() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return sb.toString();
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCodeFromUser() {
        return authCodeFromUser;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setAuthCodeFromUser(String authCodeFromUser) {
        this.authCodeFromUser = authCodeFromUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static List<String> getUserData() {
        return userData;
    }

    public static void setUserData(List<String> userData) {
        LoginController.userData = userData;
    }
}
