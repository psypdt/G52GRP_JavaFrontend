package sample.functionality.forms.formSending;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.junit.Test;

import java.io.IOException;
import java.text.Normalizer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FormSenderTest {

    @Test
    public void printHtmlToConsole() {

    }

    @Test
    public void login_incorrect_password() {
        String username = "psyjm9";
        String password = "asdf";
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            form.login(username, password);
            fail();
        }
        catch (IOException e)
        {
            assertThat(e.getMessage(), is("Error: Invalid username or password"));
        }
    }

    @Test
    public void login_incorrect_username() {
        String username = "asdf";
        String password = "ThisIsMyPassword";
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            form.login(username, password);
            fail();
        }
        catch (IOException e)
        {
            assertThat(e.getMessage(), is("Error: Invalid username or password"));
        }
    }

    @Test
    public void login_no_password() {
        String username = "asdf";
        String password = "";
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            form.login(username, password);
            fail();
        }
        catch (IOException e)
        {
            assertThat(e.getMessage(), is("Error: Please enter a password"));
        }
    }

    @Test
    public void login_no_username() {
        String username = "";
        String password = "ThisIsMyPassword";
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            form.login(username, password);
            fail();
        }
        catch (IOException e)
        {
            assertThat(e.getMessage(), is("Error: Please enter a password"));
        }
    }

    @Test
    public void check_non_existent_Element()
    {

        String element_name = "#Invalid_login_name";
        try
        {
            FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
            try
            {
                Document moodle = Jsoup.connect("https://moodle.nottingham.ac.uk/login/index.php").get();
                FormElement loginForm =  moodle.select(element_name).forms().get(0);
                form.checkElement(element_name, loginForm);
                fail();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (RuntimeException e)
        {
            assertThat(e.getMessage(), is("Unable to find " + element_name));
        }
    }

    @Test
    public void check_existent_Element()
    {

        String element_name = "#login";
        try
        {
            FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
            try
            {
                Document moodle = Jsoup.connect("https://moodle.nottingham.ac.uk/login/index.php").get();
                FormElement loginForm =  moodle.select(element_name).forms().get(0);
                form.checkElement(element_name, loginForm);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (RuntimeException e)
        {
            assertThat(e.getMessage(), is("Unable to find " + element_name));
        }
    }

    @Test
    public void fetchUsrName()
    {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        String username = "psyjm9";

        assertEquals(username, form.fetchUsrName(username));
    }

    @Test
    public void fetchPassWord() {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        String password = "asdf";

        assertEquals(password, form.fetchUsrName(password));
    }

    @Test
    public void getWebView() {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        assertNotNull(form.getWebView());
    }
}