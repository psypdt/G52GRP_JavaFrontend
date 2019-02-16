package sample.functionality.jSoupParsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;


public class Parser
{
    public Parser(){}

    /***
     * This function will the entire page, need a way to check if method fails
     * @param url The page we want to parse
     * @throws IOException Thrown due to Jsoup.connect() method
     * @deprecated This method is not in use since there is currently no reason to over parseSpecificTag()
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
    public void parseSpecificTag(String tag, String url)
    {
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
                    System.out.println("Input URL = "+doc.title());

                    writeParsedToFile(targetTags, doc);
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


    /***
     * This function will write the parsed content into a new file
     * @param tagContent List of tags that we want to save
     * @param parseDoc The Jsoup document that was used to do the parsing
     * @throws IOException FileWriter() throws exception
     */
    private void writeParsedToFile(Elements tagContent, Document parseDoc) throws IOException
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

    /***
     * This function will read the file containing the parsed site data.
     * NOTE: Not sure if this should return a String or something else, reason, returning a large string is inefficient
     * @param filePath Location of file that contains parsed data
     * @throws IOException Thrown due to BufferReader(), thrown if file not found
     */
    private void readParsedFile(String filePath) throws IOException
    {
        String fileContent = "";
        String tempstr;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        /*Reads the file into a string */
        while((tempstr = bufferedReader.readLine()) != null)
        {
            fileContent.concat(tempstr);
        }
        bufferedReader.close();
    }


}
