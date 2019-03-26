package sample.gui.startScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.gui.tabs.WebViewTab;
import sample.application.Main;

public class StartScreenController
{
    @FXML private Button open_google;
    @FXML private Button open_moodle;

    @FXML private void initialize()
    {
        /*
         * Set up the Open Google button to open a new Google tab and a new Moodle tab
         */
        open_google.setOnAction(e -> Main.getTasksPage().getTabs().add(new WebViewTab("https://www.google.com")));
        open_moodle.setOnAction(e -> Main.getTasksPage().getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk")));
    }
}
