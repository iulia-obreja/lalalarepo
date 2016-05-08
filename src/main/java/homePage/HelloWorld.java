package homePage;

import javax.faces.bean.ManagedBean;

/**
 * Created by Iulia-PC on 5/7/2016.
 */
@ManagedBean(name = "helloWorld", eager = true)
public class HelloWorld {

    public HelloWorld() {
        System.out.println("HelloWorld started!");
    }

    public String getMessage() {
        return "Hello World!";
    }
}
