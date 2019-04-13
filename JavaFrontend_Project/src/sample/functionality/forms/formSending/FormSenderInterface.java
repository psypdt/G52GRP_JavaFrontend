package sample.functionality.forms.formSending;

import javafx.scene.web.WebView;
import org.jsoup.nodes.Element;
import java.io.IOException;


/**
 * @implNote It is acceptable to just have a method for static forms, dynamic forms are not a required feature.
 */
public interface FormSenderInterface
{
    void staticFormLogin(String userName, String password) throws IOException;
    void checkElement(String name, Element elem) throws RuntimeException;
    WebView getWebView();
}
