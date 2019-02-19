package sample.functionality.parsing;

import org.junit.Test;
import sample.functionality.parsing.parser.Parser;

import static org.junit.Assert.*;

public class ParserTest
{
    @Test
    public void test_default_constructor()
    {
        Parser parser = new Parser();
        assertNotNull(parser);
    }

    @Test
    public void parseEntireHtml()
    {
        Parser parser = new Parser();

    }

    @Test
    public void parseSpecificTag()
    {
    }

    @Test
    public void test_file_exists()
    {

    }
}