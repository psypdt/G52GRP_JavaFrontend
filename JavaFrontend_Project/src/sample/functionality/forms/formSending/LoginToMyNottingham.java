package sample.functionality.forms.formSending;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLInputElement;


class FuckingLetMeIn extends Task {
    private WebEngine engine;
    private String username;
    private String password;

    FuckingLetMeIn (WebEngine engine, String username, String password) {
        this.engine = engine;
        this.username = username;
        this.password = password;
    }

    @Override
    public Object call() {
        try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }

        Element doc = engine.getDocument().getDocumentElement();

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

public class LoginToMyNottingham {

    private WebEngine webEngine;

    public void start() {
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
                    FuckingLetMeIn f = new FuckingLetMeIn(webEngine, "", "");
                    new Thread(f).start();
                }
            }
        });

        webEngine.load("https://mynottingham.nottingham.ac.uk");
    }


    public static void main(String[] args) {
        LoginToMyNottingham login = new LoginToMyNottingham();
        login.start();
    }
}
