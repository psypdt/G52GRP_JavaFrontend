package sample.functionality.forms.formSending;

import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;


public class FormSender extends Tab implements FormSenderInterface
{
    private String url;
    public WebView webpage;
    private int loginStates = 2; /*2 = form page loaded & sent the login form to server, 1 = got the final page, is it thread safe???*/

    /*If this is added to something like a button or anything that can be "listened to", then it seems like it works*/
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
//                HTMLInputElement element = (HTMLInputElement) webpage.getEngine().getDocument().getElementsByTagName("body").item(0);
//                element.click();

                /*These method will keep being repeated in the background everytime the webengine is asked to load something new*/
                if(loginStates == 2)
                {
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
                    tmp(webpage.getEngine());
                }
            }
        });
        System.out.println("Outside");
        this.url = dest;
        webpage.getEngine().load(url); /*After the tmp() runs the first time, this can be executed*/
    }



    public void tmp(WebEngine webEngine)
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
//        HTMLInputElement element = (HTMLInputElement) doc.getElementsByTagName("button").item(0);
//        element.click();
    }





    /*================================================================================================================*/

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
        webpage.getEngine().load(loginActionResponse.url().toString());
        loginStates--;
        System.out.println("LoginStates is: " + loginStates);

//        Document runp = Jsoup.connect("https://moodle.nottingham.ac.uk/theme/yui_combo.php?rollup/3.17.2/yui-moodlesimple-min.js")
//                .ignoreContentType(true)
//                .get();

//        System.out.println(runp);
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






    public static void main(String args[])
    {
        FormSender formSender = new FormSender("https://moodle.nottingham.ac.uk/index.php");
        try {
            formSender.login("psypdt", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
