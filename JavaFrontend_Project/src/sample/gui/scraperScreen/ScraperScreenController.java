package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.functionality.parsing.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

public class ScraperScreenController
{
    @FXML private AnchorPane pane;
    @FXML private VBox vertical_grid;

    private String id;
    private ArrayList<String> loginTags = new ArrayList<>();

    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button login_button;

    @FXML private void initialize() {
        login_button.setOnAction((e) -> {
            //System.out.println(username_field.getText());
            //System.out.println(password_field.getText());
            login();
        });
    }

    public void setId(String id) {
        this.id = id;
    }

    @FXML public void login() {
        JSONParser parser = new JSONParser();
        switch (id) {
            /* Set a id for the URL, so when the URL needs to be used it can only input the id, set relevant form tags*/
            case "Moodle (courses)":
                loginTags.add("#login");
                loginTags.add("#username");
                loginTags.add("#password");
                try {
                    //a.list-group-item
                    //div.m-l-1
                    JSONArray output = parser.login("https://moodle.nottingham.ac.uk/login/index.php", "div.m-l-1", username_field.getText(), password_field.getText(), loginTags);
                    System.out.println(output.toString());
                    displayElements(output);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case "Blue Castle (Grades)":
                loginTags.add("form");
                loginTags.add("#UserName");
                loginTags.add("#Password");
                try {
                    JSONArray output = parser.login("https://bluecastle-results.nottingham.ac.uk/Account/Login?ReturnUrl=%2f", "h4", username_field.getText(), password_field.getText(), loginTags);
                    System.out.println(output.toString());
                    displayElements(output);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
        }

    }

    /**
     * Creates a JavaFX representation of a JSON array representing HTML elements.
     * @param elements JSON array of HTML elements to convert to JavaFX objects
     */
    @FXML private void displayElements(JSONArray elements) {

        // set-up the primary container to hold the JavaFX objects
        vertical_grid = new VBox();
        vertical_grid.setLayoutX(100);
        vertical_grid.setLayoutY(100);

        // remove the login form and put the primary container into the view
        pane.getChildren().clear();
        pane.getChildren().add(vertical_grid);

        // iterate through the JSON array and display each element
        for (int i = 0; i < elements.length(); i++) {
            try {
                displayElement(elements.getJSONObject(i), vertical_grid);
            } catch (JSONException e) {
                e.printStackTrace();
                vertical_grid.getChildren().add(new Text("Could not load :("));
            }
        }
    }

    /**
     * Create an equivalent JavaFX object for a HTML element and displays it within a given JavaFX container element.
     * @param element JSON representation of a HTML element
     * @param container JavaFX container element to display within
     */
    @FXML private void displayElement(JSONObject element, Pane container) {

        Node n = null;
        String type;

        // check the HTML tag name
        try {
            type = element.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
            type = "p";
        }

        try {
            switch (type.toLowerCase()) {
                case "button": {
                    // HTML button -> FXML button

                    // check for element text
                    String buttonText = "";
                    if (element.has("text")) buttonText = element.getString("text");
                    if (buttonText.equals("")) buttonText = "Click me!";  // default text if none found

                    n = new Button(buttonText);
                    break;
                }
                case "a": {
                    // HTML a -> FXML hyperlink

                    // check for element text
                    String linkText = "";
                    if (element.has("text")) linkText = element.getString("text");
                    if (linkText.equals("")) linkText = "Click me!";  // default text if none found

                    n = new Hyperlink(linkText);
                    break;
                }
                default: {
                    // check for element text
                    String text = "";
                    if (element.has("text")) text = element.getString("text");
                    if (text.equals("")) text = "This is text";  // default text if none found

                    n = new Text(text);
                    break;
                }
            }

            // append the JavaFX object to the displayable container
            container.getChildren().add(n);

            // recurse through any and all children of the element
            if (element.has("children")) {
                JSONArray children = element.getJSONArray("children");

                // new container, one level deeper
                Pane childContainer = new VBox();
                for (int i = 0; i < children.length(); i++) {
                    displayElement(children.getJSONObject(i), childContainer);
                }
                container.getChildren().add(childContainer);
            }

        } catch (JSONException e) { e.printStackTrace(); }
    }
}
