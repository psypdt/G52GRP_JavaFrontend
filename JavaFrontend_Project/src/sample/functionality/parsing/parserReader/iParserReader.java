package sample.functionality.parsing.parserReader;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @apiNote The {{@link #readParsedFile(String)}} function is no longer in use since an alternative way of passing tags
 *          has been favoured over this specific method.
 */
public interface iParserReader
{
    ArrayList<String> readParsedFile(String filePath) throws IOException;
}
