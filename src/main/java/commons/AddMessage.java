package commons;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by Iulia-PC on 6/14/2016.
 */
public class AddMessage {

    public static void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
