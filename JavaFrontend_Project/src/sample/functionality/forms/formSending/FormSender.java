package sample.functionality.forms.formSending;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLInputElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class FormSender extends Tab implements FormSenderInterface
{
    private String m_Url; /*The url where the form is located*/
    private WebView m_WebView; /*Where the website will be displayed*/
    private WebEngine m_WebEngine;
    private Map<String, String> m_LoginCookies; /*Contains login cookies to keep session alive & allow link navigation*/
    private ArrayList<String> m_FormTags; /*Convention: [0] = form_name, [1] = username_tag, [2] = password_tag*/

    /***
     * Constructor for the FormSender Class, Creates a new WebView (and WebEngine)
     * Automatically loads the {@code dest} url
     * @param dest The URL where the staticFormLogin form is located
     * @param isStatic Is the login form of the page created dynamically, or does it exist in the source (static)
     * @param username The users username for the website
     * @param password The users password for the website
     * @param loginTags The tags detailing the specific form elements (form name, username field, etc.)
     */
    public FormSender(String dest, Boolean isStatic, String username, String password, ArrayList<String> loginTags)
    {
        super();
        m_Url = dest;

        m_WebView = new WebView();
        m_FormTags = loginTags;

        m_WebEngine = m_WebView.getEngine();
        m_WebEngine.setJavaScriptEnabled(true);

        m_WebEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            //System.out.printf("oldVal = %s, newVal = %s\n", oldValue, newValue);

            // when page has finished loading
            if (newValue == Worker.State.SUCCEEDED)
            {
                //DEBUGGING : print the current URL
                //System.out.printf("current URL = %s\n", m_WebEngine.getLocation());

                if (!isStatic) //Eg MyNottingham
                {
                    System.out.println("NOT STATIC");
                    dynamicFormLogin autoLogin = new dynamicFormLogin(m_WebEngine, username, password);
                    new Thread(autoLogin).start();
                }
            }
        });

        /*Handle the login for static pages*/
        if(isStatic)
        {
            try {
                staticFormLogin(username, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_WebView.getEngine().load(m_Url); /*After the printHtmlToConsole() runs the first time, this can be executed*/

    }


    /**
     * Getter for the {@code m_LoginCookies Map}
     * @return {@code m_LoginCookies}
     */
    public Map<String, String> getLoginCookies() {
        return m_LoginCookies;
    }


    /***
     * This method is what will automate the staticFormLogin process for the user
     * @param userName The users userName from the fxml form
     * @param password The users password from the fxml form
     * @throws IOException the {@code parseMultipleTags()} function can throw an {@code IOException}
     * NOTE: We may want to find a way to read in the parameter names as well for {@code checkElement()}
     * and {@code loginFormResponse.parse().select(SomeVar).first()}
     */
    @Override
    public void staticFormLogin(String userName, String password) throws IOException
    {
        /*Constants used in this example*/
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
        final String LOGIN_FORM_URL = m_Url;
        final String USERNAME = userName;
        final String PASSWORD = password;

        /*Go to staticFormLogin page*/
        Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                .timeout(8000)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();


        /*Find the staticFormLogin form via its tag*/
        /*For BlueCastle, this tag is "form"*/
        /*For MyNottingham, #login or form#login*/
        /*For Moodle, #login*/
        FormElement loginForm = (FormElement)loginFormResponse.parse().select(m_FormTags.get(0)).first();
        checkElement("Login Form", loginForm);

        /*Complete the staticFormLogin form, user name & password*/
        /*For BlueCastle this is called #UserName*/
        /*For MyNottingham, #userid*/
        /*For Moodle #username*/
        Element loginField = loginForm.select(m_FormTags.get(1)).first();
        checkElement("Login Field", loginField);
        loginField.val(USERNAME);

        /*For BlueCastle this field is "#Password"*/
        /*For MyNottingham, #pwd*/
        /*For Moodle, #password*/
        Element passwordField = loginForm.select(m_FormTags.get(2)).first();
        checkElement("Password Field", passwordField);
        passwordField.val(PASSWORD);


        /*Send staticFormLogin form*/
        Connection.Response loginActionResponse = loginForm.submit()
                .cookies(loginFormResponse.cookies())
                .userAgent(USER_AGENT)
                .execute();


        System.out.println("Url after staticFormLogin was: " + loginActionResponse.url());
        this.m_Url = loginActionResponse.url().toString();

        m_LoginCookies = loginFormResponse.cookies(); //NEW LINE - Saves the cookies into a map.
    }


    /***
     * This method checks if the element we want to use is actually available in the source of the page
     * @param tagName The name of the tag that we want to check, Ex. {@code "Login Form"}
     * @param tagElement The actual {@code Jsoup.Element} that we want to pass and check if it exists
     * @throws RuntimeException Throws a {@code RuntimeException} if the element is not found
     */
    @Override
    public void checkElement(String tagName, Element tagElement) throws RuntimeException
    {
        if (tagElement == null)
        {
            throw new RuntimeException("Unable to find " + tagName);
        }
    }


    /***
     * To enforce encapsulation, this getter can be used to get the WebView if needed
     * @return Returns the {@code WebView} of the {@link FormSender} object
     */
    @Override
    public WebView getWebView()
    {
        return this.m_WebView;
    }

}




/**
 * This class is used to automate the login process for site that dynamically load their login form like MyNottingham
 */
class dynamicFormLogin extends Task
{
    private WebEngine engine;
    private String username;
    private String password;

    private final long TIMEOUT = 10 * 1000;  // 10 seconds

    /**
     * NOTE: We should look into how we can cleanse this information once we are done using it
     * This is the constructor for the {@code AutomateMyNottinghamLogin} class
     * @param engine The {@code WebEngine} that is being used to display the sites
     * @param username The Username
     * @param password The Password
     */
    dynamicFormLogin(WebEngine engine, String username, String password) {
        this.engine = engine;
        this.username = username;
        this.password = password;
    }


    @Override
    public Object call() {

        // timing variables
        long    startTime = System.currentTimeMillis(),
                timeElapsed,
                millisToWait = 100;

        // HTML elements
        org.w3c.dom.Element doc;
        NodeList forms;
        HTMLFormElement loginForm = null;

        /* Compare and contrast with the following JavaScript code:
         *
         *   let forms = document.getElementsByTagName("form");
         *   let loginForm = forms[1];
         *
         *   let inputs = loginForm.elements;
         *   inputs[2].value = username;
         *   inputs[3].value = password;
         *   loginForm.submit();
         */

        do {  // keep polling for the form, or time-out

            System.out.println("Finding the form...");
            try { Thread.sleep(millisToWait); } catch (Exception e) { e.printStackTrace(); }

            if (engine.getDocument() != null) {
                doc = engine.getDocument().getDocumentElement();
                forms = doc.getElementsByTagName("form");
                loginForm = (HTMLFormElement)forms.item(1);
            }

            //millisToWait *= 2;  // exponential back-off
            millisToWait += 100;  // incremental back-off
            timeElapsed = System.currentTimeMillis() - startTime;

        } while (loginForm == null && timeElapsed < TIMEOUT);  // time-out after 10 seconds

        // if the page didn't time-out, continue with auto-login
        if (loginForm != null) {
            System.out.println("Found it!");

            HTMLCollection inputs = loginForm.getElements();
            ((HTMLInputElement)inputs.item(2)).setValue(username);
            ((HTMLInputElement)inputs.item(3)).setValue(password);
            loginForm.submit();
        } else {
            System.out.println("Page timed out");
        }

        return null;
    }
}
