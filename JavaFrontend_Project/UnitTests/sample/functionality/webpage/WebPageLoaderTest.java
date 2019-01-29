package sample.functionality.webpage;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WebPageLoaderTest
{
    @Test
    void test_default_constructor()
    {

    }

    @Test
    void test_specific_constructor_correct_args()
    {

    }


    /**
     *If an empty string is passed, then default to a predefined URL
     */
    @Test
    void test_specific_constructor_empty_str()
    {
        WebPageLoader loader = new WebPageLoader("");

        assertEquals("Invalid URL", loader.getHomeURL(), true);
    }

    /**
     * This method will test a connection to the website is made
     */
    @Test
    void test_site_connection()
    {

    }

    @Test
    public void start()
    {

    }

    @Test
    public void load_webpage()
    {

    }

    @Test
    public void reload_web_page()
    {

    }

}