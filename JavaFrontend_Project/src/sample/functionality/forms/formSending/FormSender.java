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
 * in {@link Parser()} are not synced
 */
public class FormSender extends Tab implements FormSenderInterface
{
    private String url;
    private WebView webView; /*Where the website will be displayed*/
    private WebEngine webEngine;
    public Parser parser = new Parser();
    private ArrayList<String> list = new ArrayList<>();

    /***
     * Constructor for the FormSender Class, Creates a new WebView (and WebEngine)
     * Automatically loads the {@code dest} url
     * @param dest The URL where the staticFormLogin form is located
     */
    public FormSender(String dest)
    {
        super();
        url = dest;
        webView = new WebView();

        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            // DEBUGGING : check current load status
            //System.out.printf("oldVal = %s, newVal = %s\n", oldVal.toString(), newVal.toString());

            // when page has finished loading
            if (newValue == Worker.State.SUCCEEDED)
            {
                //DEBUGGING : print the current URL
                System.out.printf("current URL = %s\n", webEngine.getLocation());

                if (webEngine.getLocation().equals("https://mynottingham.nottingham.ac.uk/psp/psprd/EMPLOYEE/EMPL/h/?tab=PAPP_GUEST"))
                {
                    dynamicFormLogin autoLogin = new dynamicFormLogin(webEngine, "", "");
                    new Thread(autoLogin).start();
                }
            }
        });

        webView.getEngine().load(url); /*After the printHtmlToConsole() runs the first time, this can be executed*/
    }


    /***
     * This method prints the HTML after a successful staticFormLogin to the console
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
        final String LOGIN_FORM_URL = url;
        final String USERNAME = userName;
        final String PASSWORD = password; /*Need a way to save the password safely, maybe en/decryption?*/

        /*Go to staticFormLogin page*/
        Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                .timeout(8000)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();

        /*Find the staticFormLogin form via its tag*/
        /*For blue castle, this tag is "form"*/
        FormElement loginForm = (FormElement)loginFormResponse.parse().select("form#staticFormLogin").first();
        checkElement("Login Form", loginForm);

        /*Complete the staticFormLogin form, user name & password*/
        /*For blue castle this is called #UserName*/
        Element loginField = loginForm.select("userid").first();
        checkElement("Login Field", loginField);
        loginField.val(USERNAME);

        /*For blue castle this field is "#Password"*/
        Element passwordField = loginForm.select("#Password").first();
        checkElement("Password Field", passwordField);
        passwordField.val(PASSWORD);


        /*Send staticFormLogin form*/
        Connection.Response loginActionResponse = loginForm.submit()
                .cookies(loginFormResponse.cookies())
                .userAgent(USER_AGENT)
                .execute();


        System.out.println("Url after staticFormLogin was: " + loginActionResponse.url());
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
    }


    /***
     * This method checks if the element we want to use is actually available in the source of the page
     * @param name The name of the tag that we want to check, Ex. {@code "Login Form"}
     * @param elem The actual {@code Jsoup.Element} that we want to pass and check if it exists
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
     * This method ca be used to get the password input from the fxml form
     * @param pwd The text form the FXML files password field
     * @return Returns the password from the FXML file
     */
    public String fetchPassWord(String pwd)
    {
        return pwd;
    }


    /***
     * To enforce encapsulation, this getter can be used to get the WebView if needed
     * @return Returns the {@code WebView} of the {@link FormSender} object
     */
    @Override
    public WebView getWebView()
    {
        return this.webView;
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

    /**
     * This is the constructor for the {@code AutomateMyNottinghamLogin} class
     * @param engine The {@code WebEngine} that is being used to display the sites
     * @param username The Username, should be changed asap (shouldn't be plane text)
     * @param password The Password, should be changed asap (shouldn't be plane text)
     */
    dynamicFormLogin(WebEngine engine, String username, String password) {
        this.engine = engine;
        this.username = username;
        this.password = password;
    }

    /**
     * This method will try to fetch the login form from websites that generate login froms dynamically
     * The from will then be populated with the {@code username} and {@code password}
     * The form will then be submitted to log the user into the respective website 
     * @return {@code null}, this is overwriting the {@code call()} method from {@code Object} which has no return type
     */
    @Override
    public Object call() {
        try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }

        org.w3c.dom.Element doc = engine.getDocument().getDocumentElement();

        /* compare and contrast with the following JavaScript code:
         *
         *   let forms = document.getElementsByTagName("form");
         *   let loginForm = forms[1];
         *   let inputs = loginForm.elements;
         *   inputs[2].value = username;
         *   inputs[3].value = password;
         *   loginForm.submit();
         */
        NodeList forms = doc.getElementsByTagName("form");
        HTMLFormElement loginForm = (HTMLFormElement)forms.item(1);
        HTMLCollection inputs = loginForm.getElements();
        ((HTMLInputElement)inputs.item(2)).setValue(username);
        ((HTMLInputElement)inputs.item(3)).setValue(password);
        loginForm.submit();

        return null;
    }
}
