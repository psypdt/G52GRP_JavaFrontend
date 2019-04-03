package sample.functionality.parsing.parser;

import org.json.JSONException;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.functionality.forms.formSending.FormSender;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Map;

public class JSONParser {
    private String url;
    private Document document;
    private FormSender login;

    public JSONParser()
    {
    }

    public String login(String loginurl, String tags, String username, String password) throws IOException {
        this.url = loginurl;
        Map<String, String> loginCookies = null;
        login = new FormSender(url, true, username, password);
        //login.staticFormLogin(username, password);
        while(loginCookies == null) {
            System.out.println("a");
            loginCookies = login.getLoginCookies();
        }
        //login.staticFormLogin(username, password);

        url = login.getWebView().getEngine().getLocation();

        System.out.println(url);


        String pageAsString = "";
        try {
            document = Jsoup.connect(url)
                    .cookies(loginCookies)
                    .get();
            pageAsString = this.getJSON(tags);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageAsString;

    }

    /**
     * Getter for the web-site "{@code url}" HTML
     * @return Returns the HTML that was fetched by the background thread
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Fetches HTML (using {@code getHTML()}) and constructs the layout for the JSON result, defines elements
     * @implNote IT MAY BE NECESSARY TO MAKE THIS RETURN A JSON-OBJECT INSTEAD OF A STRING
     * @param cssQuery The Tag that is of interest. Simply: "The tag who's content we want to get"
     * @return String that expresses the contents a {@code JSONObject} would contain
     */
    public String getJSON(String cssQuery)
    {
        //getHTML();

        JSONStringer stringer = new JSONStringer();

        try {
            stringer.array();
            Elements elements = document.select(cssQuery);

            /*Loop through all elements found that correspond to the tag "cssQuery" */
            for (Element e : elements)
            {
                System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());
                parseElement(stringer, e);
            }

            stringer.endArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringer.toString();
    }

    /**
     * This is a recursive method. The method populates the JSON fields, meaning it fills in the values the parsed
     * tags, hence {@code stringer.object().key("type")}...
     * @param stringer A {@code JSONStringer} object that helps building the JSON object
     * @param element The element that will be explored (check if {@code element} has children that should be parsed)
     * @throws JSONException {@code JSONStringer.object()} throws exception if nesting is too deep (more than 20 levels)
     * or if object starts at the incorrect place, accessing element out of range
     */
    private void parseElement(JSONStringer stringer, Element element) throws JSONException
    {
        /*The stringer object helps us construct the JSONObject, here we create a new object, hence .object()*/
        stringer.object()
                .key("type")
                .value(element.tagName());

        /*If the element has text, we add it under the key "text" */
        if (!element.ownText().equals("")) {
            stringer.key("text");
            stringer.value(element.ownText());
        }

        /*If the element has children (contains other tags) then recursively call parseElement() to nest children
         * (Ex. <h1>contain h2 <h2>contained </h2> </h1>) ->
         * [{"type":"h1","text":"contain h2","children":[{"type":"h2", "text":"contained"}]}]
         * */
        if (element.children().size() > 0)
        {
            stringer.key("children").array();

            /*For every child do a recursive function call*/
            for (Element child : element.children())
            {
                parseElement(stringer, child);
            }

            stringer.endArray();
        }
        /*Object has been fully constructed*/
        stringer.endObject();
    }

}
