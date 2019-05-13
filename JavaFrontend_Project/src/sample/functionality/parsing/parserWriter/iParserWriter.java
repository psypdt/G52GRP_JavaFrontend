package sample.functionality.parsing.parserWriter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @apiNote The {@link #writeParsedToFile(Elements, Document)} method is no longer in use since parsed elements are no
 *          longer written into files.
 */
public interface iParserWriter
{
    void writeParsedToFile(Elements tagContent, Document parseDoc) throws IOException;
}
