package sample.functionality.parsing.parserWriter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public interface ParserWriterInterface
{
    void writeParsedToFile(Elements tagContent, Document parseDoc) throws IOException;
}
