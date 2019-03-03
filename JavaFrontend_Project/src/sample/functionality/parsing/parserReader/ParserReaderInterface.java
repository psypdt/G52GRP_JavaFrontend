package sample.functionality.parsing.parserReader;

import java.io.IOException;
import java.util.ArrayList;

public interface ParserReaderInterface
{
    ArrayList<String> readParsedFile(String filePath) throws IOException;
}
