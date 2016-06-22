package login;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by Iulia-PC on 6/7/2016.
 */

@ManagedBean(name = "navController", eager = true)
@ViewScoped
public class NavigationController implements Serializable{

    private static final long serialVersionUID = 1520318172495977648L;

    private static String getURL(String newPage){

        FacesContext ctx = FacesContext.getCurrentInstance();
        if(ctx == null){
            if (newPage.equals("studentLogin.xhtml") || newPage.equals("adminLogin.xhtml")){
                return "/home/" + newPage;
            }
        }

        HttpServletRequest servletRequest = (HttpServletRequest) ctx.getExternalContext().getRequest();
        String fullURI = servletRequest.getRequestURI();

        String splitter[] = fullURI.split("/");

        if(newPage.equals("studentLogin.xhtml") || newPage.equals("adminLogin.xhtml")){
            if(splitter[1].equals("home")){
                return "/" + newPage;
            }
        }

        if(splitter.length > 2 && splitter[2].equalsIgnoreCase("templates")){
            return newPage;
        }
        return "templates/" + newPage;
    }

    public static String goToRepartizare(){
        return getURL("repartizare.xhtml?faces-redirect=true");
    }

    public static String goToAdmin(){
        return getURL("admin.xhtml?faces-redirect=true");
    }

    public static String goToLoginAgain(){
        return getURL("studentLogin.xhtml");
    }

    public static String goToStudent(){
        return getURL("studentAn2View.xhtml?faces-redirect=true");
    }

    public static String goToStudentAn3(){
        return getURL("studentAn3View.xhtml?faces-redirect=true");
    }

    public static String goToInscriereOptionale(){
        return getURL("inscriereOptionale.xhtml?faces-redirect=true");
    }

    public static String goToAdminLogin(){
        return getURL("adminLogin.xhtml");
    }
}
