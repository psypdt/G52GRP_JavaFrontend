package sample.functionality.parsing.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.functionality.forms.formSending.FormSender;
import java.util.ArrayList;
import java.util.Map;


public class JSONParser
{
    private String m_Url;
    private Document m_Document; /*Stores the HTML that will be parsed temporarily*/
    private FormSender m_FormSender; /*FormSender allows user login to occur right before parsing, without state change*/

    /**
     * Constructor for {@link JSONParser}
     */
    public JSONParser()
    {
    }

    /**
     * This method is used to log the user into a website, and then parses a set of tags after the m_FormSender is complete
     * @param loginUrl Url to the website where the m_FormSender form is located
     * @param tags The tag that will be parsed after the m_FormSender is completed
     * @param username The users username
     * @param password The users password
     * @param loginTags The tags required to find the m_FormSender form
     * @return {@code JSONArray pageAsString}, this is a JSONArray that contains the parsed information
     */
    public JSONArray login(String loginUrl, String tags, String username, String password, ArrayList<String> loginTags)
    {
        this.m_Url = loginUrl;
        Map<String, String> loginCookies = null;
        m_FormSender = new FormSender(m_Url, true, username, password, loginTags);
        //m_FormSender.staticFormLogin(username, password);
        while(loginCookies == null)
        {
            System.out.println("a");
            loginCookies = m_FormSender.getLoginCookies();
        }
        //m_FormSender.staticFormLogin(username, password);

        m_Url = m_FormSender.getWebView().getEngine().getLocation();

        //DEBUG:
        System.out.println(m_Url);

        JSONArray pageAsString = new JSONArray();

        try
        {
            m_Document = Jsoup.connect(m_Url)
                    .cookies(loginCookies)
                    .get();
            pageAsString = this.getJSONArray(tags);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageAsString;
    }

    /**
     * Getter for the web-site "{@code m_Url}" HTML
     * @return Returns the HTML that was fetched by the background thread
     */
    public Document getDocument() {
        return m_Document;
    }


    /**
     * This is used in the {@code JSONParser.m_FormSender(...)} method, this method constructs a JSONArray and populates it
     * @param cssQuery This is the tag that will be used to parse the m_Document (contains HTML of website)
     * @return {@code JSONArray} that contains the parsed tags
     */
    private JSONArray getJSONArray(String cssQuery) {
        JSONArray parsedTagJsonArray = new JSONArray();

        Elements elements = m_Document.select(cssQuery); /*Elements that are of interest*/

        for (Element e : elements)
        {
            //DEBUG: Prints the element found, used to see if correct element was found
            System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());
            parsedTagJsonArray.put(parseElement(e));
        }

        return parsedTagJsonArray;
    }



    /**
     * NOTE: If nesting is too deep (more than 20 levels) or if object starts at the incorrect place, accessing element out of range
     * This is a recursive method. The method populates the JSON fields, meaning it fills in the values the parsed
     * tags
     * This method is used by {@code getJSONArray(String cssQuery)}
     * @param element The element that will be explored (check if {@code element} has children that should be parsed)
     */
    private JSONObject parseElement(Element element)
    {
        JSONObject obj = new JSONObject();

        //This constructs the JSONObject
        try
        {
            obj.put("type", element.tagName());

            if (!element.ownText().equals(""))
            {
                System.out.println("Element: " + element.tagName() + ", Text: " + element.ownText());
                obj.put("text", element.ownText());
            }

            if (element.tagName().equals("a") && element.attributes().get("href") != null)
            {
                obj.put("href", element.attributes().get("href"));
            }

            if (element.children().size() > 0)
            {
                //DEBUG: Used to show that a child has been correctly added to the JSONObject
                System.out.println(element.children().toString());

                JSONArray childrenArray = new JSONArray();

                /*For every child do a recursive function call*/
                for (Element child : element.children()) { childrenArray.put(parseElement(child)); }

                obj.put("children", childrenArray);
            }
        } catch (JSONException e) { e.printStackTrace(); }

        return obj;
    }
}
