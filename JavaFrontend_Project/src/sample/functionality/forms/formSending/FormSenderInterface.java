package sample.functionality.forms.formSending;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface FormSenderInterface
{
    Document login(String userName, String password) throws IOException;

}
