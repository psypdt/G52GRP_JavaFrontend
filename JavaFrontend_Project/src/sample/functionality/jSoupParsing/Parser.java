package sample.functionality.jSoupParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

/*Note: It may be necessary to run this as a thread so that the GUI doesn't freeze up*/
public class Parser implements Runnable
{
    public Parser()
    {
    }

    /***
     * This function will the entire page, need a way to check if method fails
     * @param url The page we want to parse
     * @throws IOException Thrown due to Jsoup.connect() method
     */
    public void parseEntireHtml(String url) throws IOException
    {
        String content;

        if(!url.equals(""))
        {
            Document doc = Jsoup.connect(url).get();

            content = doc.outerHtml();
            System.out.println(content);
        }
        else
        {
            Document doc = Jsoup.connect("https://moodle.nottingham.ac.uk").get();

            content = doc.outerHtml();
            System.out.println(content);
        }
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
        /*This class exists because it allows us to "pass parameters" to the thread*/
        class ScrapingTask implements Runnable
        {
            String inputTag;
            String inputUrl;
            ScrapingTask(String tag, String url)
            {
                inputTag = tag;
                inputUrl = url;
            }

            /*Try to make the code inside the thread a function*/
            @Override
            public void run()
            {
                try
                {
                    Document doc = Jsoup.connect(inputUrl).get();
                    Elements targetTags = doc.select(inputTag);

                    /*Prints all tags that matched the description specified in parseSpecificTag()*/
                    for(Element docTag : targetTags)
                    {
                        System.out.println(docTag.outerHtml());
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        /*Run the contents inside run(), would be better if we had a function to call, makes things more clear (maybe)*/
        Thread t1 = new Thread(new ScrapingTask(tag, url));
        t1.start();
    }

    /*Could create a nested class inside parse functions, that would allow parameters to be accessed from the function itself*/
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
