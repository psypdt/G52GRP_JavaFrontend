package sample.functionality.parsing.parserWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;


//NOTE: This class is currently not used, this class allows parsed data to be read into a file.
public class ParserWriter implements ParserWriterInterface
{
    /**
     * Constructor for {@link ParserWriter}, doesn't execute any methods when called.
     */
    public ParserWriter(){}

    /***
     * This function will write the parsed content into a new file.
     * @implNote This method has the capability to save an image to the local file system, but it is not used currently.
     * @param tagContent List of tags that we want to save.
     * @param parseDoc The {@code Jsoup} document that was used to do the parsing.
     * @throws IOException {@code FileWriter()} throws IOException when writing fails.
     */
    @Override
    public void writeParsedToFile(Elements tagContent, Document parseDoc) throws IOException
    {
        //Developer may want to change this via a library that handles spaces and other types of punctuation.
        String fileName = parseDoc.title().replace(":", "-")+".html";
        String fullPath = "./resource_parsed_files/"+fileName;

        //Create a new statement that will allow us to save images if the specific condition is met.
//        URL url = new URL("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
//        BufferedImage img = ImageIO.read(url);
//        File file = new File("./resource_parsed_files/thing.png");
//        ImageIO.write(img, "png", file);

        if(new File(fullPath).exists())
        {
            throw new IOException("File \"" + fileName + "\" at location \"" +fullPath + "\" already exists");
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullPath, false));
//        bufferedWriter.write(String.valueOf(parseDoc));

        for (Element tag : tagContent)
        {
            bufferedWriter.write(tag+"\n");
        }

        bufferedWriter.close();
    }
}
