package sample.functionality.parsing.parser;

import org.json.JSONException;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class DocumentGetterThread extends Thread {

    private String url;
    private Document document;

    public DocumentGetterThread(String url) {
        this.url = url;
    }

    public void run() {
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return document;
    }
}

public class HTMLtoJSON {

    private String url;
    private Document d;

    public HTMLtoJSON (String url) {
        this.url = url;
    }

    private void getHTML() {
        DocumentGetterThread t = new DocumentGetterThread(url);
        t.run();

        try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        d = t.getDocument();
    }

    public String getJSON(String cssQuery) {

        getHTML();

        JSONStringer stringer = new JSONStringer();

        try {
            stringer.array();

            Elements elements = d.select(cssQuery);
            for (Element e : elements) {
                System.out.println("Element: " + e.tagName() + ", Text: " + e.ownText());
                parseElement(stringer, e);
            }

            stringer.endArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringer.toString();
    }

    private void parseElement(JSONStringer stringer, Element e) throws JSONException {
        stringer.object()
                .key("type")
                .value(e.tagName());

        if (!e.ownText().equals("")) {
            stringer.key("text");
            stringer.value(e.ownText());
        }

        if (e.children().size() > 0) {
            stringer.key("children").array();

            for (Element child : e.children()) {
                parseElement(stringer, child);
            }

            stringer.endArray();
        }

        stringer.endObject();
    }

    public static void main(String[] args) {
        HTMLtoJSON h2j = new HTMLtoJSON("http://example.com");

        String s = h2j.getJSON("p");
        System.out.println(s);
    }
}
