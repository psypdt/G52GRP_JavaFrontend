package sample.functionality.webpage;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/*Note: It may be necessary to run this as a thread so that the GUI doesn't freeze up*/
public class Parser
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
            System.out.println(docTag.attr(tag));
            System.out.println(docTag.text());
        }
    }

}
