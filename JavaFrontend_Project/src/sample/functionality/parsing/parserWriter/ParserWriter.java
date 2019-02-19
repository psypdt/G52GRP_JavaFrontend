package sample.functionality.parsing.parserWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ParserWriter implements ParserWriterInterface
{
    public ParserWriter(){}

    /***
     * This function will write the parsed content into a new file
     * @param tagContent List of tags that we want to save
     * @param parseDoc The Jsoup document that was used to do the parsing
     * @throws IOException FileWriter() throws exception
     */
    @Override
    public void writeParsedToFile(Elements tagContent, Document parseDoc) throws IOException
    {
        String fileName = parseDoc.title()+".html";
        String fullPath = "./resource_parsed_files/"+fileName;

        if(new File(fullPath).exists())
        {
            throw new IOException("File \"" + fileName + "\" at location \"" +fullPath + "\" already exists");
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullPath, false));

        for (Element tag : tagContent)
        {
            bufferedWriter.write(tag+"\n");
        }
        bufferedWriter.close();
    }
}