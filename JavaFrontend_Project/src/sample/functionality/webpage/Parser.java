package sample.functionality.webpage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/*Note: It may be necessary to run this as a thread so that the GUI doesn't freeze up*/
public class Parser
{
    public Parser()
    {

    }

    public void parseURL(String url)
    {
        String content;
        try
        {
            Document doc = Jsoup.connect("https://www.google.com").get();

            content = doc.outerHtml();
            System.out.println(content);
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }

    }

}
