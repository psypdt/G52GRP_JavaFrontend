package sample.functionality.parsing.parser;

import org.json.JSONObject;
import java.io.IOException;

public interface ParserInterface
{
    void parseEntireHtml(String url) throws IOException;
    JSONObject parseSpecificTag(String tag, String url);
    String standardiseFileName(String fname) throws IOException;
}
