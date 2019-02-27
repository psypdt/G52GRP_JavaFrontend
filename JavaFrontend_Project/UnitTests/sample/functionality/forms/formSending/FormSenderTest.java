package sample.functionality.forms.formSending;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.junit.Test;
import java.io.IOException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class FormSenderTest
{
    /*We can debate if this should be the way we handle an invalid input, maybe we have a method to change the URL?*/
    @Test
    public void blank_constructor_arg()
    {
        try
        {
            FormSender formSender = new FormSender("");
        }
        catch (ExceptionInInitializerError e)
        {
            assertThat(e.getMessage(), is("Error, destination URL is required"));
        }
    }


    @Test
    public void create_FormSender_obj()
    {
        FormSender formSender = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        assertNotNull(formSender);
    }


    /*Currently not used*/
    @Test
    public void printHtmlToConsole()
    {

    }


    @Test
    public void login_incorrect_password()
    {
        String username = "psyjm9";
        String password = "notMyPassword";
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
    public void login_incorrect_username()
    {
        String username = "NotMyUsername";
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


    /*Note that the login method will need to call the parsing class and return JSON, this needs to be amended*/
    @Test
    public void login_no_password()
    {
        String username = "psypdt";
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
    public void login_no_username()
    {
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
    public void check_empty_Element()
    {
        String element_name = "";

        try
        {
            FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
            try
            {
                Document moodle = Jsoup.connect("https://moodle.nottingham.ac.uk/login/index.php").get();
                FormElement loginForm = moodle.select(element_name).forms().get(0);
                form.checkElement(element_name, loginForm);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (RuntimeException e)
        {
            assertThat(e.getMessage(), is("Empty Tag is an illegal input"));
        }
    }


    @Test
    public void fetchUsrName()
    {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        String username = "psyjm9";

        assertEquals(username, form.fetchUsrName(username));
    }


    /*This method and teh fetchUsrName method should be revised, make sure that they actually do what we want*/
    @Test
    public void fetchPassWord()
    {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        String password = "ThePassword";

        assertEquals(password, form.fetchUsrName(password));
    }

    @Test
    public void fetchUsername()
    {
        FormSender form =  new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        String username = "TheUsername";

        assertEquals(username, form.fetchUsrName(username));
    }


    @Test
    public void getWebView()
    {
        FormSender form = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");
        assertNotNull(form.getWebView());
    }



}