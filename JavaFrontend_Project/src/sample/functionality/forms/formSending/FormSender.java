package sample.functionality.forms.formSending;

import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class FormSender extends Tab implements FormSenderInterface
{
    private String url;
    private WebView webpage;
    private int loginStates = 2; /*2 = form page loaded & sent the login form to server, 1 = got the final page, is it thread safe???*/

    /*If this is added to something like a button or anything that can be "listened to", then it seems like it works*/

    /***
     * Constructor for the FormSender Class, Creates a new WebView (and WebEngine)
     * Automatically loads the {@code dest} url
     * @param dest The URL where the login form is located
     */
    public FormSender(String dest)
    {
        super();
        webpage = new WebView();
        webpage.getEngine().setJavaScriptEnabled(true);

        webpage.getEngine().getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            System.out.println(newValue);
            /*Make sure this is joined, otherwise the main finishes before this and kills the thread, hence it doesn't print*/
            if (newValue == Worker.State.SUCCEEDED)
            {
                System.out.println(newValue);

                /*These method will keep being repeated in the background everytime the webengine is asked to load something new*/
                if(loginStates == 2)
                {
                    try
                    {
                        login("", "");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                if(loginStates == 1)
                {
                    printHtmlToConsole(webpage.getEngine());
                }
            }
        });
        System.out.println("Outside");
        this.url = dest;
        webpage.getEngine().load(url); /*After the printHtmlToConsole() runs the first time, this can be executed*/
    }


    /***
     * This method prints the HTML after a successful login to the console
     * @param webEngine The {@code WebView}'s {@code WebEngine} is passed to extract it's contents
     */
    public void printHtmlToConsole(WebEngine webEngine)
    {
        org.w3c.dom.Document doc = webEngine.getDocument();

        /*This prints everything to the command line*/
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "html");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc),
                    new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    /***
     * This method is what will automate the login process for the user
     * @param userName The users userName from the fxml form
     * @param password The users password from the fxml form
     * @throws IOException the {@code parse()} function can throw an {@code IOException}
     */
    @Override
    public void login(String userName, String password) throws IOException
    {
        /*Constants used in this example*/
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
        final String LOGIN_FORM_URL = "https://moodle.nottingham.ac.uk/login/index.php";
        final String USERNAME = userName;
        final String PASSWORD = password;

        /*Go to login page*/
        Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();


        /*Find the login from via its tag*/
        FormElement loginForm = (FormElement)loginFormResponse.parse().select("#login").first();
        checkElement("Login Form", loginForm);

        /*Complete the login form, user name & password*/
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


        System.out.println("Url after login was: " + loginActionResponse.url());
        this.url = loginActionResponse.url().toString();

        Connection.Response check = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();

        webpage.getEngine().load(url);
        loginStates--;
    }


    /***
     * This method checks if the element we want to use is actually available in the source of the page
     * @param name The name of the tag that we want to check, Ex. {@code "Login Form"}
     * @param elem The actual {@code Jsoup.Element} that we want to pass
     * @throws RuntimeException Throws a {@code RuntimeException} if the element is not found
     */
    public static void checkElement(String name, Element elem) throws RuntimeException
    {
        if (elem == null)
        {
            throw new RuntimeException("Unable to find " + name);
        }
    }


    /***
     * This method can be used to get the username input from fxml form
     * @param uName The text from the FXML files username field
     * @return Returns the username from the FXML file
     */
    public String fetchUsrName(String uName)
    {
        return uName;
    }


    /***
     * This method ca be used to get the passeord input from the fxml form
     * @param pwd The text form the FXML files password field
     * @return Returns the password from the FXML file
     */
    public String fetchPassWord(String pwd)
    {
        return pwd;
    }


    /***
     * To enforce encapsulation, this getter can be used to get the WebView if needed
     * @return Returns the WebView of the FormSender object
     */
    public WebView getWebView()
    {
        return this.webpage;
    }

}
