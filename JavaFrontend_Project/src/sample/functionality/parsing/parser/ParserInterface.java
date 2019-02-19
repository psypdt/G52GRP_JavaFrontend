package sample.functionality.parsing.parser;

import java.io.IOException;

public interface ParserInterface
{
    void parseEntireHtml(String url) throws IOException;
    void parseSpecificTag(String tag, String url);
    String standardiseFileName(String fname) throws IOException;
}
