package sample.functionality.forms.formSending;

import javafx.scene.web.WebView;
import org.jsoup.nodes.Element;
import java.io.IOException;


public interface FormSenderInterface
{
    void staticFormLogin(String userName, String password) throws IOException;
    void checkElement(String name, Element elem) throws RuntimeException;
    WebView getWebView();
}
