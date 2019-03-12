package sample.functionality.parsing.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;

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
    public void parse_existing_column_div_tag()
    {
        Parser parser = new Parser();
        JSONObject object = parser.parseSpecificTag("div.col-md-12", "https://moodle.nottingham.ac.uk/staticFormLogin/index.php");
        assertNotNull(object);
    }


    @Test
    public void test_correct_json_content()
    {
        String tag = "div.col-md-12";
        Parser parser = new Parser();
        JSONObject object = parser.parseSpecificTag(tag, "https://moodle.nottingham.ac.uk/staticFormLogin/index.php");

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


    @Test
    public void test_non_existent_tag()
    {
        String illegalTag = "DeFiNeTlY-N0T-a-7Ag";
        Parser parser = new Parser();

        try
        {
            JSONObject object = parser.parseSpecificTag(illegalTag, "https://moodle.nottingham.ac.uk/staticFormLogin/index.php");
        }
        catch (RuntimeException except)
        {
            assertThat(except.getMessage(), is("Tag \"" + illegalTag + "\" doesnt exist."));
        }
    }


    @Test
    public void test_empty_tag()
    {
        String emptyTag = "";
        Parser parser = new Parser();

        try
        {
            JSONObject object = parser.parseSpecificTag(emptyTag, "https://moodle.nottingham.ac.uk/staticFormLogin/index.php");
        }
        catch (RuntimeException except)
        {
            assertThat(except.getMessage(), is("NOTICE: Empty Tags are not allowed."));
        }
    }


    /*This test should ensure that this is not possible, the parseMultipleTags() function should achieve this*/
    @Test
    public void test_two_tags()
    {
        String tags = "title, h1";
        Parser parser = new Parser();

        JSONObject object = parser.parseSpecificTag(tags, "https://moodle.nottingham.ac.uk/staticFormLogin/index.php");
        try
        {
            assertEquals("{\"title, h1\": [\n" +
                    "    \"University of Nottingham Moodle: Log in to the site\",\n" +
                    "    \"Log in to moodle...\"\n" +
                    "]}", object.toString(4));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }




    @Test
    public void test_empty_file()
    {
        String emptyFile = "./test_resources/emptyTagFile.txt";
        String url = "https://moodle.nottingham.ac.uk/staticFormLogin/index.php";
        Parser parser = new Parser();

        try
        {
            parser.parseMultipleTags(emptyFile, url);
        }
        catch (RuntimeException exception)
        {
            assertThat(exception.getMessage(), is("Error: The file \"" + emptyFile + "\" contains no tags"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_title_h1_tags()
    {
        String tagFile = "./test_resources/TitleH1File.txt";
        String url = "https://moodle.nottingham.ac.uk/staticFormLogin/index.php";
        Parser parser = new Parser();

        ArrayList<String> jsonObject = null;
        try {
            jsonObject = parser.parseMultipleTags(tagFile, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("{\"h1\":[\"Log in to moodle...\"]," +
                        "\"title\":[\"University of Nottingham Moodle: Log in to the site\"]}", jsonObject);
    }


    @Test
    public void test_h2_h4_tags()
    {
        String tagFile = "./test_resources/H2H4.txt";
        String url = "https://moodle.nottingham.ac.uk/staticFormLogin/index.php";
        Parser parser = new Parser();

        ArrayList<String> jsonObject = null;
        try {
            jsonObject = parser.parseMultipleTags(tagFile, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("{\"h2\":[\"Log in\"]," + "\"h4\":[\"Site Information\",\"Get in touch\"]}",
                jsonObject);
    }


    @Test
    public void test_h1_h2_h4_tags()
    {
        String tagFile = "./test_resources/H1H2H4.txt";
        String url = "https://moodle.nottingham.ac.uk/staticFormLogin/index.php";
        Parser parser = new Parser();

        ArrayList<String> jsonObject = null;
        try {
            jsonObject = parser.parseMultipleTags(tagFile, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("{\"h1\":[\"Log in to moodle...\"]," + "\"h2\":[\"Log in\"]," +
                        "\"h4\":[\"Site Information\",\"Get in touch\"]}", jsonObject);
    }


    /*Test if we are able to pull out nested tags and label them as "children" of the parent tag*/
    @Test
    public void test_nested_table_tags()
    {
        String tagFile = "./test_resources/Table.txt";
        String url = "https://moodle.nottingham.ac.uk/staticFormLogin/index.php";
        Parser parser = new Parser();

        ArrayList<String> jsonObject = null;
        try {
            jsonObject = parser.parseMultipleTags(tagFile, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("[[{\"type\":\"table\",\"children\":[{\"type\":\"tbody\",\"children\"" +
                ":[{\"type\":\"tr\",\"children\":[{\"type\":\"td\",\"children\"" +
                ":[{\"type\":\"div\",\"children\":[{\"type\":\"strong\",\"children\"" + ":[{\"type\":\"span\"," +
                "\"text\":\"Your current browser is incompatible with this new version of Moodle.\"," +
                "\"children\":[{\"type\":\"a\",\"text\":\"More Information\"}]}]}]}]}]}]}]}]]", jsonObject.toString());
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