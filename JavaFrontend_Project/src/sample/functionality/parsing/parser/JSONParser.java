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


public class JSONParser implements ParserInterface
{
    private String m_Url;
    private Document m_Document; // Stores the HTML that will be parsed temporarily.
    private FormSender m_FormSender; // FormSender allows user login to occur right before parsing, without state change.

    /**
     * Constructor for {@link JSONParser}.
     * @implSpec Should it be necessary that this class takes input on construction, create an overloaded constructor.
     * @implNote Currently the constructor does nothing since there is no need for it do execute anything.
     */
    public JSONParser()
    {
    }

    /**
     * This method is used to log the user into a website and parse the site for the specified {@code tags}.
     * @implSpec By default this method will parse for the {@code tags} immediately after the login has completed.
     * @implNote This method creates a {@link FormSender} object and assigns it to {@link #m_FormSender}.
     * @implNote This method uses {@link #getJSONArray(String)}.
     * @param loginUrl Url to the website where the m_FormSender form is located.
     * @param tags The tag that will be parsed after the m_FormSender is completed.
     * @param username The users username for the website.
     * @param password The users password for the website.
     * @param loginTags The tags required to find the m_FormSender form.
     * @param isStatic Specify if the website dynamically generates its login form, or if it is static.
     * @return {@code pageAsString}, this is a {@link JSONArray} that contains the parsed information.
     */
    @Override
    public JSONArray login(String loginUrl, String tags, String username, String password, ArrayList<String> loginTags, boolean isStatic)
    {
        this.m_Url = loginUrl;
        Map<String, String> loginCookies = null;
        m_FormSender = new FormSender(m_Url, isStatic, username, password, loginTags); // Currently only static works.
        //m_FormSender.staticFormLogin(username, password);

        while(loginCookies == null)
        {
            System.out.println("a");
            loginCookies = m_FormSender.getLoginCookies();
        }
        //m_FormSender.staticFormLogin(username, password);

        m_Url = m_FormSender.getWebView().getEngine().getLocation();

        //DEBUG: Print current URL.
        //System.out.println(m_Url);

        JSONArray pageAsString = new JSONArray();

        try // Try connecting to the current website to get its contents.
        {
            // Save the website contents into m_Document.
            m_Document = Jsoup.connect(m_Url)
                    .cookies(loginCookies)
                    .get();

            pageAsString = this.getJSONArray(tags);
        }
        catch (Exception e) { e.printStackTrace(); }

        return pageAsString;
    }


    /**
     * Getter for the web-site "{@link #m_Url}" HTML.
     * @return Returns the HTML that was fetched by the background thread.
     */
    public Document getDocument() {
        return m_Document;
    }


    /**
     * @implNote This is used in {@link JSONParser#login(String, String, String, String, ArrayList, boolean)}.
     * @implNote This method constructs & populates a new {@link JSONArray} with parsed tags.
     * @param cssQuery This is the tag that will be used to parse the m_Document (contains HTML of website).
     * @return {@link JSONArray} that contains the contents of the parsed tag(s).
     */
    private JSONArray getJSONArray(String cssQuery)
    {
        JSONArray parsedTagJsonArray = new JSONArray();
        Elements elements = m_Document.select(cssQuery); //Elements that are of interest.

        for (Element e : elements)
        {
            //DEBUG: Prints the element found, used to see if correct element was found
            //System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());

            parsedTagJsonArray.put(parseElement(e));
        }

        return parsedTagJsonArray;
    }



    /**
     * @implNote If tag nesting is too deep (more than 20 levels, restriction place by {@link Jsoup})
     *          or if object starts at the incorrect place (accessing element out of range) then a crash can occur.
     * @implNote This is a recursive method. The method populates the JSON fields, ie. fills in values of parsed tags.
     *          This method is used by {@code getJSONArray(String cssQuery)}.
     * @param element The element that will be explored (check if {@code element} has children that should be parsed).
     * @return {@code JSONObject} representing the {@code element} and all its children.
     */
    private JSONObject parseElement(Element element)
    {
        JSONObject jsonParsedTag = new JSONObject();

        // This constructs the JSONObject.
        try
        {
            jsonParsedTag.put("type", element.tagName());

            if (!element.ownText().equals(""))
            {
                //DEBUG: Print the tag name and tag contents.
                System.out.println("Element: " + element.tagName() + ", Text: " + element.ownText());

                jsonParsedTag.put("text", element.ownText());
            }

            if (element.tagName().equals("a") && element.attributes().get("href") != null)
            {
                jsonParsedTag.put("href", element.attributes().get("href"));
            }

            if (element.children().size() > 0) // Check if tag has children.
            {
                //DEBUG: Used to show that a child has been correctly added to the JSONObject.
                System.out.println(element.children().toString());

                JSONArray childrenArray = new JSONArray();

                // For every child (should it exist) do a recursive function call.
                for (Element child : element.children())
                {
                    childrenArray.put(parseElement(child));
                }

                jsonParsedTag.put("children", childrenArray);
            }
        } catch (JSONException e) { e.printStackTrace(); }

        return jsonParsedTag;
    }
}
