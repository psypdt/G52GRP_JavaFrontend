package sample.functionality.parsing;

import java.io.IOException;

public interface ParserInterface
{
    void parseEntireHtml(String url) throws IOException;
    void parseSpecificTag(String tag, String url);
}
