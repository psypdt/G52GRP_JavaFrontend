package sample.runApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * This class is responsible for setting up the functionality of the buttons on the home screen.
 */
public class ApplicationController
{
    @FXML private TabPane tab_pane;
    @FXML private Button    button_moodle,
                            button_portal,
                            button_grades_modules,
                            button_blue_castle,
                            button_mynottingham;
    @FXML private Button load_database;
    @FXML private Button button_settings;

    /**
     * Hook up functionality to the buttons on the home screen.
     */
    @FXML private void initialize()
    {
        button_moodle.setOnAction(e -> Main.getGuiHandler().openTab("Moodle (courses)"));
        button_blue_castle.setOnAction(e -> Main.getGuiHandler().openTab("Blue Castle (Grades)"));
    }

    /**
     * Get a reference to the tab pane to allow for the opening and closing of tabs.
     * @return The primary tab pane on the screen.
     */
    @FXML public TabPane getTabPane() { return tab_pane; }
}
