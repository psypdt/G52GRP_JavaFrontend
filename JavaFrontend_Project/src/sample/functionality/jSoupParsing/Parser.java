package sample.functionality.jSoupParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/*Note: It may be necessary to run this as a thread so that the GUI doesn't freeze up*/
public class Parser implements Runnable
{
    public Parser()
    {
    }


    public void parseEntireHtml(String url) throws IOException
    {
        String content;

        Document doc = Jsoup.connect("https://www.google.com").get();

        content = doc.outerHtml();
        System.out.println(content);
    }

    /***
     * This method should allow us to parse specific elements from a website
     * NOTE: It appears that we are getting the "INSPECT ELEMENT" output
     * @param tag The tag that we want to extract
     * @param url The website that we want to parse from
     * @throws IOException This is a side effect of the Jsoup.connect() method
     */
    public void parseSpecificTag(String tag, String url) throws IOException
    {
        Document doc = Jsoup.connect(url).get();
        Elements targetTags = doc.select(tag);

        for(Element docTag : targetTags)
        {
            System.out.println(docTag.outerHtml());
//            System.out.println(docTag.attr(tag));
//            System.out.println(docTag.text());
        }
    }

    @Override
    public void run()
    {
        Parser parser = new Parser();

        try
        {
            parser.parseEntireHtml("");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
