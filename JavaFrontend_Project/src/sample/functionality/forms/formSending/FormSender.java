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
    private String m_Url; // The url where the form is located.
    private WebView m_WebView; // Where the website will be displayed.
    private WebEngine m_WebEngine; // The WebEngine responsible for loading URLs, be it FXML or other.
    private Map<String, String> m_LoginCookies; // Contains login cookies to keep session alive & allow link navigation.
    private ArrayList<String> m_FormTags; // Convention: [0] = form_name, [1] = username_tag, [2] = password_tag.


    /***
     * @implSpec Constructor for the FormSender Class, Creates a new {@code WebView} and {@code WebEngine}.
     * @implSpec Automatically loads the {@code dest} url via the {@code WebEngine}.
     * @implNote Constructor calls {{@link #staticFormLogin(String, String)}} and/or
     *          {@link DynamicFormLogin#DynamicFormLogin(WebEngine, String, String)}.
     * @param dest The URL where the staticFormLogin form is located.
     * @param isStatic Is the login form of the page created dynamically, or does it exist in the source (static).
     * @param username The users username for the website.
     * @param password The users password for the website.
     * @param loginTags The tags detailing the specific elements of the login form (form name, username field, etc.).
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
            //DEBUG: Print the state of the WebEngine.
            //System.out.printf("oldVal = %s, newVal = %s\n", oldValue, newValue);

            // When page has finished loading.
            if (newValue == Worker.State.SUCCEEDED)
            {
                //DEBUGGING : print the current URL.
                //System.out.printf("current URL = %s\n", m_WebEngine.getLocation());

                if (!isStatic) // Any website that has a dynamically loaded form, Ex. MyNottingham.
                {
                    //DEBUG: Confirm that this condition was met.
                    //System.out.println("NOT STATIC");

                    DynamicFormLogin autoLogin = new DynamicFormLogin(m_WebEngine, username, password);
                    new Thread(autoLogin).start(); // Create new thread to complete auto login.
                }
            }
        });

        // Handle the login for static pages.
        if(isStatic)
        {
            try {
                staticFormLogin(username, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_WebView.getEngine().load(m_Url); // After the printHtmlToConsole() runs the first time, this can be executed.
    }


    /**
     * Getter for the {@link #m_LoginCookies} {@link Map}.
     * @return {@link #m_LoginCookies}, which is a {@link Map} containing the login cookies when the form is sent.
     */
    public Map<String, String> getLoginCookies() { return m_LoginCookies; }


    /***
     * This method is what will automate the {@code staticFormLogin} process for the user.
     * @implNote This method calls {@link #checkElement(String, Element)} and sets {@link #m_Url}
     * @param userName The users userName from the fxml form.
     * @param password The users password from the fxml form.
     * @throws IOException {@link Jsoup#connect(String)} can throw an {@code IOException}, as can the
     *        {@link Connection.Response#parse()}.
     */
    @Override
    public void staticFormLogin(String userName, String password) throws IOException
    {
        // Constants used in this example.
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
        final String LOGIN_FORM_URL = m_Url;

        // Go to staticFormLogin page.
        Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                .timeout(8000)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();


        // Find the staticFormLogin form via its tag, can be found by inspecting relevant HTML.
        // For BlueCastle, this tag is "form".
        // For MyNottingham, #login or form#login.
        // For Moodle, #login.
        FormElement loginForm = (FormElement)loginFormResponse.parse().select(m_FormTags.get(0)).first();
        checkElement("Login Form", loginForm);

        // Complete the staticFormLogin form, username & password.
        // For BlueCastle this is called #UserName.
        // For MyNottingham, #userid.
        // For Moodle #username.
        Element loginField = loginForm.select(m_FormTags.get(1)).first();
        checkElement("Login Field", loginField);
        loginField.val(userName);

        // For BlueCastle this field is "#Password".
        // For MyNottingham, #pwd.
        // For Moodle, #password.
        Element passwordField = loginForm.select(m_FormTags.get(2)).first();
        checkElement("Password Field", passwordField);
        passwordField.val(password);


        // Send staticFormLogin form to the website the user wishes to access.
        Connection.Response loginActionResponse = loginForm.submit()
                .cookies(loginFormResponse.cookies())
                .userAgent(USER_AGENT)
                .execute();

        //DEBUG: Used to check what url was received as a response.
        System.out.println("Url after staticFormLogin was: " + loginActionResponse.url());
        this.m_Url = loginActionResponse.url().toString();

        m_LoginCookies = loginFormResponse.cookies(); // NEW LINE - Saves the cookies into a map.
    }


    /***
     * This method checks if the {@link Element} that we parse for is actually available in the source of the page.
     * @param tagName The name of the tag that we want to check, Ex. {@code "Login Form"}.
     * @param tagElement The {@link Element} that we want to pass and check if it exists.
     * @throws RuntimeException If the {@code tagElement} is not found.
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
     * Getter for {@link #m_WebView}.
     * @return Returns the {@link #m_WebView} of the {@link FormSender} object.
     */
    @Override
    public WebView getWebView() { return this.m_WebView; }

}




/**
 * @implSpec Currently this class uses incremental-backoff.
 * @implNote This class automates the login process for sites that dynamically load the login form like MyNottingham.
 */
class DynamicFormLogin extends Task
{
    private WebEngine engine;
    private String username;
    private String password;
    private final long TIMEOUT = 10 * 1000;  // 10 second timeout.

    /**
     * This is the constructor for the {@link DynamicFormLogin} class.
     * @implSpec The user data may not be cleansed quickly enough.
     * @param engine The {@link WebEngine} that is being used to display the sites.
     * @param username The Username for the website.
     * @param password The Password for the website.
     */
    DynamicFormLogin(WebEngine engine, String username, String password)
    {
        this.engine = engine;
        this.username = username; //This may not be a good idea, shouldn't store this information.
        this.password = password;
    }


    /**
     * @implNote This method uses Incremental-backoff, using another method is up to the party extending this software.
     * @implNote The method will timeout after 10 seconds have elapsed and having failed to fetch the form.
     * @implNote At the moment the application user will not know if the login-process timed out.
     * This method will attempt to fetch the dynamically created form, populate it and send it back with the user details.
     * @return This method will return {@code null} since no return value is necessary.
     */
    @Override
    public Object call()
    {
        // Timing variables to allow repeated login attempts.
        long startTime = System.currentTimeMillis();
        long timeElapsed; // Time elapsed since last login attempt by the method.
        long millisToWait = 100;

        // HTML elements.
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

        do // keep polling for the form, or time-out.
        {
            //DEBUG: Verify that the process has started.
            //System.out.println("Finding the form...");

            try { Thread.sleep(millisToWait); } catch (Exception e) { e.printStackTrace(); }

            if (engine.getDocument() != null)
            {
                doc = engine.getDocument().getDocumentElement();
                forms = doc.getElementsByTagName("form");
                loginForm = (HTMLFormElement)forms.item(1);
            }

            //millisToWait *= 2;  // exponential back-off.

            millisToWait += 100;  // incremental back-off.
            timeElapsed = System.currentTimeMillis() - startTime;

        } while (loginForm == null && timeElapsed < TIMEOUT);  // time-out after 10 seconds.

        // if the page didn't time-out, continue with the auto-login process.
        if (loginForm != null)
        {
            //DEBUG: Confirm that the login-form has been successfully fetched.
            System.out.println("Found it!");

            HTMLCollection inputs = loginForm.getElements();
            ((HTMLInputElement)inputs.item(2)).setValue(username);
            ((HTMLInputElement)inputs.item(3)).setValue(password);
            loginForm.submit();
        }
        else
        {
            //DEBUG: Inform developer that page timed out.
            System.out.println("Page timed out");
        }

        return null;
    }
}
