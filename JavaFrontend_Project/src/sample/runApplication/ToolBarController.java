package sample.runApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/*This is responsible for controlling the Tool bar, aka. fixed "Tasks" & "Start" buttons*/
public class ToolBarController
{
    @FXML private TabPane tab_pane;
    @FXML private Button open_moodle_button;
    @FXML private Button blue_castle_button;

    @FXML private void initialize()
    {
        open_moodle_button.setOnAction(e -> Main.getApp().openTab("Moodle (courses)"));
        blue_castle_button.setOnAction(e -> Main.getApp().openTab("Blue Castle (Grades)"));
    }

    @FXML public TabPane getTabPane() { return tab_pane; }
}
