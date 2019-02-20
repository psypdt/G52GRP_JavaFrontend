package sample.functionality.forms.formSending;

import javafx.scene.web.WebView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.IOException;


public class FormSender implements FormSenderInterface
{
    private String url;
    private static WebView webpage;

    /*If this is added to something like a button or anything that can be "listened to", then it seems like it works*/
    public FormSender(String dest)
    {

    }


    /***
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public void login(String userName, String password) throws IOException
    {
        /*Constants used in this example*/
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
        final String LOGIN_FORM_URL = "https://moodle.nottingham.ac.uk/login/index.php";
        final String USERNAME = userName;
        final String PASSWORD = password;

        //Go to login page
        Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();

        // Fill the login form
        // Find the form first...
        FormElement loginForm = (FormElement)loginFormResponse.parse()
                .select("#login").first();
        checkElement("Login Form", loginForm);

        Element loginField = loginForm.select("#username").first();
        checkElement("Login Field", loginField);
        loginField.val(USERNAME);

        Element passwordField = loginForm.select("#password").first();
        checkElement("Password Field", passwordField);
        passwordField.val(PASSWORD);


        /*Send login form*/
        Connection.Response loginActionResponse = loginForm.submit()
                .cookies(loginFormResponse.cookies())
                .userAgent(USER_AGENT)
                .execute();

        System.out.println(loginActionResponse.parse().html());
    }


    /***
     * 
     * @param name
     * @param elem
     */
    public static void checkElement(String name, Element elem)
    {
        if (elem == null) {
            throw new RuntimeException("Unable to find " + name);
        }
    }
}
