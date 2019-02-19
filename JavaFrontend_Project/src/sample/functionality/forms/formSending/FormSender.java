package sample.functionality.forms.formSending;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sample.functionality.parsing.parserWriter.ParserWriter;

import java.io.IOException;

public class FormSender implements FormSenderInterface
{
    private String url;

    FormSender(String dest)
    {
        this.url = dest;
    }


    /***
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Document login(String userName, String password) throws IOException
    {
        Document document = Jsoup.connect(url)
                .data("username", userName)
                .data("password", password)
                .userAgent("Mozilla")
                .post();

        return document;
    }


    public static void main(String[] args)
    {
        FormSender formSender = new FormSender("https://moodle.nottingham.ac.uk/login/index.php");

        try
        {
            Document m = formSender.login("psypdt", "");

            ParserWriter parserWriter = new ParserWriter();
            parserWriter.writeEntireHtmlToFile(m);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
