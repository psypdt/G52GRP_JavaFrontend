package sample.runApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * This is responsible for controlling the Tool bar, aka. fixed "Tasks" & "Start" buttons
 */
public class ApplicationController
{
    @FXML private TabPane tab_pane;
    @FXML private Button open_moodle_button;
    @FXML private Button blue_castle_button;

    /**
     * Opening the tab by using the if that we set
     */
    @FXML private void initialize()
    {
        open_moodle_button.setOnAction(e -> Main.getGuiHandler().openTab("Moodle (courses)"));
        blue_castle_button.setOnAction(e -> Main.getGuiHandler().openTab("Blue Castle (Grades)"));
    }

    /**
     * To generate Tap Pane
     * @return
     */
    @FXML public TabPane getTabPane() { return tab_pane; }
}
