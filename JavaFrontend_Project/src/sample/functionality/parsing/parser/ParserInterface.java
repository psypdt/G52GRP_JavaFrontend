package sample.functionality.parsing.parser;

import org.json.JSONArray;
import java.util.ArrayList;


/**
 * @implNote The {@link #login(String, String, String, String, ArrayList, boolean)} function must return some
 * {@link JSONArray}, it is expected that parsing occurs directly after the user has logged in, if this is not the case
 * then there should be a function that navigates through the page until parsing has become feasible.
 */
public interface ParserInterface
{
    JSONArray login(String loginUrl, String tags, String username, String password, ArrayList<String> loginTags, boolean isStatic);
}
