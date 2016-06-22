package login;


import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Created by Iulia-PC on 6/15/2016.
 */
@ManagedBean(name = "ldap", eager = true)
@ApplicationScoped
public class LdapAuthentication {

//    private String host = "ldap://172.17.17.15";
//    private String baseDN = "dc=info,dc=uaic,dc=ro";
//    private String username = "iulia.obreja";
//    private String password = "";
//    private String dn;

    public void authentication() {

        String baseDN = "dc=info,dc=uaic,dc=ro";
        String username = "iulia.obreja";
        String password = "";
        String dn;
        dn = "uid=" + username + ",ou=people," + baseDN;
        String host = "ldap://172.17.17.15";

        Hashtable<String, String> environment =
                new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, host);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, dn);
        environment.put(Context.SECURITY_CREDENTIALS, password);
        try {
            DirContext authContext =
                    new InitialDirContext(environment);

            System.out.println("user is authenticated");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logare reusita", "Bravooo");
            RequestContext.getCurrentInstance().showMessageInDialog(message);

        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logare nereusita", "Authentication Exception");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (NamingException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logare nereusita", "Naming Exception");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            ex.printStackTrace();
        }
    }
}
