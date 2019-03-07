package sample.functionality.forms.formSending;

import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import sample.functionality.parsing.parser.Parser;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/**
 * NOTE: There is an issue with our threads, they are not synced, so we need to resolve this (has to do with race conditions)
 * ALSO: Note that this is probably because the webengine can't keep up with the other threads, OR because the nested threads
 * in {@code Parser()} are not synced
 */

public class FormSender extends Tab implements FormSenderInterface
{
    private String url;
    private WebView webView; /*Where the website will be displayed*/
    private int loginStates = 2; /*2 = page loaded & sent login form to server, 1 = got the final page*/
    public Parser parser = new Parser();
    private ArrayList<String> list = new ArrayList<>();

    /***
     * Constructor for the FormSender Class, Creates a new WebView (and WebEngine)
     * Automatically loads the {@code dest} url
     * @param dest The URL where the login form is located
     */
    public FormSender(String dest)
    {
        super();
        webView = new WebView();
        webView.getEngine().setJavaScriptEnabled(true);

        webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            System.out.println(newValue);
            /*Make sure this is joined, otherwise the main finishes before this and kills the thread, hence it doesn't print*/
            if (newValue == Worker.State.SUCCEEDED)
            {
                System.out.println(newValue);

                /*These method will keep being repeated in the background everytime the webengine is asked to load something new*/
                if(loginStates == 2) /*All of this needs to be cleaned, it's messy and hard to understand*/
                {
                    /*Why is this here? Need to refactor, make code clear.*/
                    try
                    {
                        login("psypdt", "");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                if(loginStates == 1)
                {
//                    printHtmlToConsole(webView.getEngine());

                    try {
                        list = parser.parseMultipleTags("./resource_parsed_files/loginFile.txt", url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("LIST IS: " + list.toString());
                }
            }
        });
        System.out.println("Outside");
        this.url = dest;
        /*rm to test if login works, it appears that this has partially contributed to the issue */
//        webView.getEngine().load(url); /*After the printHtmlToConsole() runs the first time, this can be executed*/
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
        final String PASSWORD = password; /*Need a way to save the password safely, maybe en/decryption?*/

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
        list = parser.parseMultipleTags("./resource_parsed_files/loginFile.txt", url);
        System.out.println("LIST IS: " + list.toString());
        /*
        Connection.Response check = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();
           */
        webView.getEngine().load(url);
        loginStates--;
    }


    /***
     * This method checks if the element we want to use is actually available in the source of the page
     * @param name The name of the tag that we want to check, Ex. {@code "Login Form"}
     * @param elem The actual {@code Jsoup.Element} that we want to pass
     * @throws RuntimeException Throws a {@code RuntimeException} if the element is not found
     */
    @Override
    public void checkElement(String name, Element elem) throws RuntimeException
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
    @Override
    public WebView getWebView()
    {
        return this.webView;
    }

}
