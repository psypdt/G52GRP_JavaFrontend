package sample.functionality.parsing.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class ParserTest
{

    @Test
    public void parseEntireHtml()
    {
    }

    @Test
    public void parseSpecificTag()
    {
    }



    /*Check that the object exists*/
    @Test
    public void parse_existing_column_div_tag() throws InterruptedException
    {
        Parser parser = new Parser();
        JSONObject object = parser.parseSpecificTag("div.col-md-12", "https://moodle.nottingham.ac.uk/login/index.php");
        assertNotNull(object);
    }


    @Test
    public void test_correct_json_content()
    {
        String tag = "div.col-md-12";
        Parser parser = new Parser();
        JSONObject object = parser.parseSpecificTag(tag, "https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            assertEquals(object.toString(4), "{\"div.col-md-12\": [\n" +
                    "    \"Username Password Remember username Log in\",\n" +
                    "    \"Forgotten your username or password? Cookies must be enabled in your browser\"\n" +
                    "]}");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    /*Wont work because writing images isn't implemented yet, it's commented otu*/
    @Test
    public void parse_image_myNottingham_data()
    {
        Parser parser = new Parser();
        parser.parseSpecificTag("img[src$=.png]", "https://mynottingham.nottingham.ac.uk/psp/psprd/EMPLOYEE/EMPL/h/?tab=PAPP_GUEST");
    }



    @Test
    public void standardiseFileName_empty_str_exception()
    {
        String input = "";
        Parser parser = new Parser();

        try
        {
            parser.standardiseFileName(input);
            fail();
        }
        catch (IOException e)
        {
            assertThat(e.getMessage(), is("No file Name for file"));
        }
    }


    @Test
    public void standardiseFileName_1backSlash()
    {
        String input = "abcd\\dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    @Test
    public void standardiseFileName_2backSlash()
    {
        String input = "abcd\\\\dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd--dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_1forwardSlash()
    {
        String input = "abcd/dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_colon()
    {
        String input = "abcd:dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_star()
    {
        String input = "abcd*dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_question_mark()
    {
        String input = "abcd?dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_quotation()
    {
        String input = "abcd\"dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_lessThan()
    {
        String input = "abcd<dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_greaterThan()
    {
        String input = "abcd>dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_bitShift_operator()
    {
        String input = "abcd|dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_space()
    {
        String input = "abcd dc";
        Parser parser = new Parser();

        try
        {
            assertEquals("abcd-dc", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_complex1()
    {
        String input = "ab*cd:d c";
        Parser parser = new Parser();

        try
        {
            assertEquals("ab-cd-d-c", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void standardiseFileName_complex2()
    {
        String input = "ab*cd:d c * ef   g<hi>\\j";
        Parser parser = new Parser();

        try
        {
            assertEquals("ab-cd-d-c---ef---g-hi--j", parser.standardiseFileName(input));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}