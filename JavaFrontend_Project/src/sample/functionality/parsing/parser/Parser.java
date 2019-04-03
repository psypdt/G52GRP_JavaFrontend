package sample.functionality.parsing.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.functionality.forms.formSending.FormSender;
import sample.functionality.parsing.parserReader.ParserReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public class Parser implements ParserInterface
{
    public Parser(){}


    /***
     * This function will the entire page, need a way to check if method fails
     * @param url The page we want to parse
     * @throws IOException Thrown due to Jsoup.connect() method
     * @deprecated This method is not in use since there is currently no reason to over parseSpecificTag()
     */
    @Override
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
     * @return Returns a JSONObject that will contain the parsed information
     * @deprecated This method is not safe to use, the {@code 0arseMultipleTags()} method should be used instead
     */
    @Override
    public JSONObject parseSpecificTag(String tag, String url)
    {
        if(tag.equals(""))
        {
            throw new RuntimeException("NOTICE: Empty Tags are not allowed.");
        }

        JSONObject jsonObject = new JSONObject(); /*The JSON object that will contain all the tags*/

        /*This class exists because it allows us to "pass parameters" to the thread, maybe make this a separate class file*/
        class ScrapingTask implements Runnable
        {
            private String inputTag;
            private String inputUrl;

            private ScrapingTask(String tag, String url)
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

                    /*Throw new exception if Tag doesn't exist*/
                    if(targetTags.isEmpty())
                    {
                        throw new RuntimeException("Tag \"" + inputTag + "\" doesnt exist.");
                    }

                    for(Element tagContent : targetTags)
                    {
                        jsonObject.append(tag, tagContent.text());
                    }
//                    jsonObject.append("Tag", tag); /*List of tags in object*/

//                    ParserWriter parserWriter = new ParserWriter();
//                    parserWriter.writeParsedToFile(targetTags, doc);
                }
                catch (IOException | JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        /*Run the contents inside run(), would be better if we had a function to call, makes things more clear (maybe)*/
        Thread parserThread = new Thread(new ScrapingTask(tag, url));
        parserThread.start();

        try
        {
            parserThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /***
     * This method reads a file containing specific tags, it then parses the tags and returns a JSON object
     * @param tagFile The file where all the tags that are of interest are stored
     * @param url The url where the tags are to be scraped (Site that is to be scraped)
     * @return Returns a JSONObject that stores the contents of the tags that reside in the "tagFile"
     * May need to check that only one file type (txt, html, etc.) is used
     * @throws IOException If the {@code readParsedFile()} method fails
     */
    public ArrayList<String> parseMultipleTags(String tagFile, String url) throws IOException
    {
        ArrayList<String> jsonContent;
        ArrayList<String> tagList;
        ParserReader parserReader = new ParserReader();

        tagList = parserReader.readParsedFile(tagFile);
        MultiTagScraperThread scraperThread = new MultiTagScraperThread(tagList, url);

        Thread thread = new Thread(scraperThread);
        thread.start();

        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        jsonContent = scraperThread.getJsonContent();

        return jsonContent;
    }


    /***
     * This method standardises the file names where parsed data is stored
     * This may need to be amended and made thread safe, also keep in mind that pulling form the same site is a possibility
     * May need to look into automating the naming system
     * @param fname This is the file name that we want to standardise
     * @return This will return the newly generated file name that will be used
     */
    @Override
    public String standardiseFileName(String fname) throws IOException
    {
        if(fname.equals(""))
        {
            throw new IOException("No file Name for file");
        }

        fname = fname.replace(" ", "-");
        fname = fname.replace("/", "-");
        fname = fname.replace("\\", "-");
        fname = fname.replace(":", "-");
        fname = fname.replace("*", "-");
        fname = fname.replace("?", "-");
        fname = fname.replace("\"", "-");
        fname = fname.replace("<", "-");
        fname = fname.replace(">", "-");
        fname = fname.replace("|", "-");

        return fname;
    }


    public String parsedHTML(String url2, String tags) throws IOException {
        //String url2 = "https://moodle.nottingham.ac.uk/login/index.php";
        FormSender login = new FormSender(url2, false, "", "");
        url2 = login.getWebView().getEngine().getLocation();

        System.out.println(url2);
        Map<String, String> loginCookies = login.getLoginCookies();

        String pageAsString = "";
        try {
            Document pageToScrape = Jsoup.connect(url2)
                    .cookies(loginCookies)
                    .get();

            Elements pageScraped = pageToScrape.select(tags);
            pageAsString = pageScraped.toString();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        //System.out.println(pageAsString);
        return pageAsString;

    }

}





/*Class that runs as a thread to read contents of file and parse tags*/
class MultiTagScraperThread implements Runnable
{
    private String siteUrl;
    private ArrayList<String> fileTags;
    private ArrayList<String> jsonContent = new ArrayList<>();


    /**
     * The constructor for the thread that will parse the HTML tags
     * @param tags The list of tags we extracted form the TagFile
     * @param url The website that will be scraped
     */
    public MultiTagScraperThread(ArrayList<String> tags, String url)
    {
        this.fileTags = tags;
        this.siteUrl = url;
    }


    @Override
    public void run()
    {
        //HTMLtoJSON htmLtoJSON = new HTMLtoJSON(siteUrl);

        /*Move through array list with file tags & place them in the jsonContent array list*/
        for(String tag : fileTags)
        {
            //jsonContent.add(htmLtoJSON.getJSON(tag));
        }
    }


    /**
     * Getter for the {@code jsonContent} object
     * @return  ArrayList containing all parsed tags
     */
    public ArrayList<String> getJsonContent()
    {
        return jsonContent;
    }


    /**
     * This method is used when we want to clear the {@code jsonContent} list so that we can reuse it later
     */
    public void resetJsonContent()
    {
        jsonContent.clear();
    }


    /**
     * This method resets the url to a new link, or "" if no input is given
     * @param newUrl The new site that we want to scrape
     */
    public void setSiteUrlUrl(String newUrl)
    {
        if(newUrl.isEmpty())
        {
            this.siteUrl = "";
        }
        else
        {
            this.siteUrl = newUrl;
        }
    }
}
