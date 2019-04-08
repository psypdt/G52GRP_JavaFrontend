package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.functionality.parsing.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    JSONArray output = parser.login("https://moodle.nottingham.ac.uk/login/index.php", "div.m-l-1", username_field.getText(), password_field.getText(), loginTags);
                    System.out.println(output.toString());
                    displayTags(output);
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
                    displayTags(output);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
        }

    }

    @FXML
    public void displayTags (JSONArray tags) {

        vertical_grid = new VBox();
        vertical_grid.setLayoutX(100);
        vertical_grid.setLayoutY(100);
        vertical_grid.getChildren().add(new Button("Click me!"));
        pane.getChildren().clear();
        pane.getChildren().add(vertical_grid);

        // DEBUG: check length of JSON object
        System.out.println(tags.length());

        for (int i = 0; i < tags.length(); i++) {
            try {
                JSONObject element = tags.getJSONObject(i);

                // DEBUG: check the contents of the HTML element
                System.out.println(element.toString());

                // create some FXML objects (pull this out into its own function)
                switch (element.getString("type")) {
                    // HTML Button -> FXML Button
                    case "button": {
                        vertical_grid.getChildren().add(new Button(element.getString("text")));
                        break;
                    }
                    // text elements -> FXML Text
                    default: {
                        if (element.has("text")) {
                            vertical_grid.getChildren().add(new Text(element.getString("text")));
                        }
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                vertical_grid.getChildren().add(new Text("Could not load :("));
            }
        }

    }
}
