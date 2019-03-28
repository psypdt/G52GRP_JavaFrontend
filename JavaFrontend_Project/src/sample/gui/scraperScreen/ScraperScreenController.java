package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScraperScreenController {
    private static Map<String, String> TAGS_TO_VIEWS;

    // setup a hash table to convert HTML elements to FXML nodes
    static {
        TAGS_TO_VIEWS = new HashMap<>();
        TAGS_TO_VIEWS.put("button", "Button");
    }

    @FXML private AnchorPane pane;
    @FXML private VBox vertical_grid;

    @FXML private void initialize() {
        vertical_grid = new VBox();
        pane.getChildren().add(vertical_grid);
    }

    @FXML
    public void displayTags (JSONArray tags) throws JSONException {

        // DEBUG: check length of JSON object
        System.out.println(tags.length());

        for (int i = 0; i < tags.length(); i++) {
            JSONObject element = tags.getJSONObject(i);

            // DEBUG: check the contents of the HTML element
            System.out.println(element.toString());

            // lookup the FXML object to create for a given HTML element
            String view = TAGS_TO_VIEWS.getOrDefault(element.getString("type"), "Text");

            // create some FXML objects (pull this out into its own function)
            switch (view) {
                // HTML Button -> FXML Button
                case "Button": {
                    vertical_grid.getChildren().add(new Button(element.getString("text")));
                    break;
                }
                // text elements -> FXML Text
                default: {
                    vertical_grid.getChildren().add(new Text(element.getString("text")));
                    break;
                }
            }
        }

    }
}