package sample.functionality.forms.formSending;

import java.io.IOException;


public interface FormSenderInterface
{
    void login(String userName, String password) throws IOException;
}
