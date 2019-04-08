//package sample.functionality.parsing.parser;
//
//import javafx.application.Platform;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONStringer;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import sample.functionality.forms.formSending.FormSender;
//
//import java.io.IOException;
//import java.util.Map;
//
//
///**
// * Class that runs as a background thread to fetch the HTML from a web-site
// */
//class DocumentGetterThread extends Thread
//{
//    private String url;
//    private Document document;
//    FormSender login;
//
//    /**
//     * Constructor
//     * @param url Web-page that will be parsed
//     */
//    public DocumentGetterThread(String url) {
//        this.url = url;
//    }
//
//
//    /**
//     * Will run as the background thread
//     * Will fetch the HTML from the web-page "{@code url}"
//     */
//    public void run() {
//        Platform.runLater(() -> {
//            login = new FormSender(url, true, "", "");
//            url = login.getWebView().getEngine().getLocation();
//
//
//
//            System.out.println(url);
//            Map<String, String> loginCookies = login.getLoginCookies();
//
//
//            try {
//                document = Jsoup.connect(url)
//                        .cookies(loginCookies)
//                        .get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
//
//
//    /**
//     * Getter for the web-site "{@code url}" HTML
//     * @return Returns the HTML that was fetched by the background thread
//     */
//    public Document getDocument() {
//        return document;
//    }
//}
//
//
///**
// * This class will parse the fetched HTML from the website and convert it to JSON
// */
//public class HTMLtoJSON
//{
//    private String url;
//    private Document document;
//
//    /**
//     * Sets the input {@code url} to the object's {@code url}
//     * @param url The website that will be parsed (Site that contains HTML that is of interest)
//     */
//    public HTMLtoJSON (String url) {
//        this.url = url;
//    }
//
//
//    /**
//     * This method runs the {@code DocumentGetterThread()} class,
//     * sets the {@code HTMLtoJSON} object's {@code document} to the result from the thread class,
//     * hence {@code document} = {@code documentGetter.getDocument()}
//     */
//    private void getHTML()
//    {
//        DocumentGetterThread documentGetter = new DocumentGetterThread(url);
//        documentGetter.run();
//
//        try { documentGetter.join(); } catch (InterruptedException e) { e.printStackTrace(); }
//
//        document = documentGetter.getDocument();
//    }
//
//
//    /**
//     * Fetches HTML (using {@code getHTML()}) and constructs the layout for the JSON result, defines elements
//     * @implNote IT MAY BE NECESSARY TO MAKE THIS RETURN A JSON-OBJECT INSTEAD OF A STRING
//     * @param cssQuery The Tag that is of interest. Simply: "The tag who's content we want to get"
//     * @return String that expresses the contents a {@code JSONObject} would contain
//     */
//    public String getJSON(String cssQuery)
//    {
//        getHTML();
//
//        JSONStringer stringer = new JSONStringer();
//
//        try {
//            stringer.array();
//            Elements elements = document.select(cssQuery);
//
//            /*Loop through all elements found that correspond to the tag "cssQuery" */
//            for (Element e : elements)
//            {
//                System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());
//                parseElement(stringer, e);
//            }
//
//            stringer.endArray();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return stringer.toString();
//    }
//
//
//    /**
//     * This is a recursive method. The method populates the JSON fields, meaning it fills in the values the parsed
//     * tags, hence {@code stringer.object().key("type")}...
//     * @param stringer A {@code JSONStringer} object that helps building the JSON object
//     * @param element The element that will be explored (check if {@code element} has children that should be parsed)
//     * @throws JSONException {@code JSONStringer.object()} throws exception if nesting is too deep (more than 20 levels)
//     * or if object starts at the incorrect place, accessing element out of range
//     */
//    private void parseElement(JSONStringer stringer, Element element) throws JSONException
//    {
//        /*The stringer object helps us construct the JSONObject, here we create a new object, hence .object()*/
//        stringer.object()
//                .key("type")
//                .value(element.tagName());
//
//        /*If the element has text, we add it under the key "text" */
//        if (!element.ownText().equals("")) {
//            stringer.key("text");
//            stringer.value(element.ownText());
//        }
//
//        /*If the element has children (contains other tags) then recursively call parseElement() to nest children
//        * (Ex. <h1>contain h2 <h2>contained </h2> </h1>) ->
//        * [{"type":"h1","text":"contain h2","children":[{"type":"h2", "text":"contained"}]}]
//        * */
//        if (element.children().size() > 0)
//        {
//            stringer.key("children").array();
//
//            /*For every child do a recursive function call*/
//            for (Element child : element.children())
//            {
//                parseElement(stringer, child);
//            }
//
//            stringer.endArray();
//        }
//        /*Object has been fully constructed*/
//        stringer.endObject();
//    }
//
//
//    /**
//     * This is just for testing purposes
//     * @param args Not important in this context, just a simple main function that will test the 2 classes above
//     */
//    public static void main(String[] args) {
//        HTMLtoJSON h2j = new HTMLtoJSON("moodle.nottingham.ac.uk/login/index.php");
//
//        String s = h2j.getJSON("a.list-group-item");
//        System.out.println(s);
//    }
//}
