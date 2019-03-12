package sample.functionality.forms.formSending;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLInputElement;

/**
 * This class is used to automate the staticFormLogin process for MyNottingham because it has a more complex staticFormLogin process
 */
class AutomateMyNottinghamLogin extends Task {
    private WebEngine engine;
    private String username;
    private String password;

    /**
     * This is the constructor for the {@code AutomateMyNottinghamLogin} class
     * @param engine The {@code WebEngine} that is being used to display the sites
     * @param username The Username, should be changed asap (shouldn't be plain text)
     * @param password The Password, should be changed asap (shouldn't be plain text)
     */
    AutomateMyNottinghamLogin(WebEngine engine, String username, String password) {
        this.engine = engine;
        this.username = username;
        this.password = password;
    }

    @Override
    public Object call() {
        try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }

        Element doc = engine.getDocument().getDocumentElement();

        // HTML elements
        Element doc;
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

        do {  // keep polling for the form

            System.out.println("Finding the form...");
            try { Thread.sleep(millisToWait); } catch (Exception e) { e.printStackTrace(); }

            if (engine.getDocument() != null) {
                doc = engine.getDocument().getDocumentElement();
                forms = doc.getElementsByTagName("form");
                loginForm = (HTMLFormElement)forms.item(1);
            }

            //millisToWait *= 2;  // exponential back-off
            millisToWait += 100;  // incremental back-off

        } while (loginForm == null);

        System.out.println("Found it!");

        HTMLCollection inputs = loginForm.getElements();
        ((HTMLInputElement)inputs.item(2)).setValue(username);
        ((HTMLInputElement)inputs.item(3)).setValue(password);
        loginForm.submit();

        return null;
    }
}

/*This class is no longer needed, it has been moved into FormSender*/
public class LoginToMyNottingham extends Application {

    private WebEngine webEngine;

    public void start(Stage primaryStage) {
        WebView root = new WebView();
        webEngine = root.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldVal, newVal) -> {
            // DEBUGGING : check current load status
            //System.out.printf("oldVal = %s, newVal = %s\n", oldVal.toString(), newVal.toString());

            // when page has finished loading
            if (newVal == Worker.State.SUCCEEDED) {

                //DEBUGGING : print the current URL
                System.out.printf("current URL = %s\n", webEngine.getLocation());

                if (webEngine.getLocation().equals("https://mynottingham.nottingham.ac.uk/psp/psprd/EMPLOYEE/EMPL/h/?tab=PAPP_GUEST")) {
                    dynamicFormLogin f = new dynamicFormLogin(webEngine, "", "");
                    new Thread(f).start();
                }
            }
        });

        webEngine.load("https://mynottingham.nottingham.ac.uk");

        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
