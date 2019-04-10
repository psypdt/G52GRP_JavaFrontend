package sample.functionality.parsing.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.functionality.forms.formSending.FormSender;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Map;

public class JSONParser {
    private String url;
    private Document document;
    private FormSender login;

    public JSONParser()
    {
    }

    /**
     * This method is used to log the user into a website, and then parses a set of tags after the login is complete
     * @param loginurl Url to the website where the login form is located
     * @param tags The tag that will be parsed after the login is completed
     * @param username The users username
     * @param password The users password
     * @param loginTags The tags required to find the login form
     * @return {@code JSONArray pageAsString}, this is a JSONArray that contains the parsed information
     */
    public JSONArray login(String loginurl, String tags, String username, String password, ArrayList<String> loginTags) {
        this.url = loginurl;
        Map<String, String> loginCookies = null;
        login = new FormSender(url, true, username, password, loginTags);
        //login.staticFormLogin(username, password);
        while(loginCookies == null) {
            System.out.println("a");
            loginCookies = login.getLoginCookies();
        }
        //login.staticFormLogin(username, password);

        url = login.getWebView().getEngine().getLocation();

        System.out.println(url);


        JSONArray pageAsString = new JSONArray();
        try {
            document = Jsoup.connect(url)
                    .cookies(loginCookies)
                    .get();
            pageAsString = this.getJSONArray(tags);

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
     * This is used in the {@code JSONParser.login(...)} method, this method constructs a JSONArray and populates it
     * @param cssQuery This is the tag that will be used to parse the document (contains HTML of website)
     * @return {@code JSONArray} that contains the parsed tag contents
     */
    private JSONArray getJSONArray(String cssQuery) {
        JSONArray result = new JSONArray();

        Elements elements = document.select(cssQuery);

        for (Element e : elements) {
            System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());
            result.put(parseElement(e));
        }

        return result;
    }



    /**
     * NOTE: If nesting is too deep (more than 20 levels) or if object starts at the incorrect place, accessing element out of range
     * This is a recursive method. The method populates the JSON fields, meaning it fills in the values the parsed
     * tags, hence {@code stringer.object().key("type")}...
     * @param element The element that will be explored (check if {@code element} has children that should be parsed)
     */
    private JSONObject parseElement(Element element) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("type", element.tagName());

            if (!element.ownText().equals("")) {
                System.out.println("Element: " + element.tagName() + ", Text: " + element.ownText());
                obj.put("text", element.ownText());
            }

            if (element.tagName().equals("a") && element.attributes().get("href") != null) {
                obj.put("href", element.attributes().get("href"));
            }

            if (element.children().size() > 0) {
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
