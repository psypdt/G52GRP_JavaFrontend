package sample.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import sample.application.Main;


/**
 * @implNote This class is responsible for setting up the functionality of the buttons on the home screen.
 * @implSpec Any  {@code FXML} Buttons which aren't utilised should realised by the party extending this software
 */
public class ApplicationController
{
    @FXML private TabPane tab_pane;
    @FXML private Button  button_moodle;
    @FXML private Button button_blue_castle;

    //Functionality to be added in future releases or by a third party extending this software
    @FXML private Button button_portal;
    @FXML private Button button_grades_modules;
    @FXML private Button button_mynottingham;
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
