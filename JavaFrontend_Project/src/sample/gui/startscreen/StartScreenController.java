package sample.gui.startscreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.Main;
import sample.gui.tabs.WebViewTab;

public class StartScreenController
{
    @FXML private Button open_google;

    @FXML private void initialize()
    {
        /* Set up the Open Google button to open a new Google tab */
        open_google.setOnAction(e -> Main.getTasksPage().getTabs().add(new WebViewTab("https://www.google.com")));
    }
}
