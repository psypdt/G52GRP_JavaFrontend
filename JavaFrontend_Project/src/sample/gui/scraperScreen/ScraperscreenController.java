package sample.gui.scraperScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.functionality.parsing.parser.JSONParser;

import java.io.IOException;

public class ScraperscreenController
{
    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private Button login_button;

    @FXML private void initialize() {
        login_button.setOnAction((e) -> {
            //System.out.println(username_field.getText());
            //System.out.println(password_field.getText());

            JSONParser parser = new JSONParser();
            try {
                String output = parser.login("https://moodle.nottingham.ac.uk/login/index.php", "a.list-group-item", username_field.getText(), password_field.getText());
                System.out.println(output);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
    }
}
