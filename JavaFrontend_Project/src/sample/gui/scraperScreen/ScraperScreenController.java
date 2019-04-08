package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import sample.functionality.parsing.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

public class ScraperscreenController
{
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
                    JSONArray output = parser.login("https://moodle.nottingham.ac.uk/login/index.php", "a.list-group-item", username_field.getText(), password_field.getText(), loginTags);
                    System.out.println(output.toString());
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
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }

}
