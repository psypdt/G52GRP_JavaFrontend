package sample.functionality.webpage;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 */
public class WebPageLoaderTest
{
    @Test
    void test_default_constructor()
    {
        WebPageLoader loader = new WebPageLoader();

        assertNotNull(loader);
    }

    @Test
    void test_specific_constructor_correct_args()
    {
        WebPageLoader loader = new WebPageLoader("https://google.com");

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
    public void test_start()
    {

    }

    @Test
    public void test_load_webpage()
    {

    }


    @Test
    public void test_reload_web_page()
    {

    }


    @Test
    public void test_getHomeURL()
    {

    }

    @Test
    public void test_setHomeURL()
    {

    }


}