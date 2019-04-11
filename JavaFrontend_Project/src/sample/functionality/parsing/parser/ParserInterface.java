package sample.functionality.parsing.parser;

import org.json.JSONArray;
import java.util.ArrayList;


public interface ParserInterface
{
    JSONArray login(String loginUrl, String tags, String username, String password, ArrayList<String> loginTags);
}
