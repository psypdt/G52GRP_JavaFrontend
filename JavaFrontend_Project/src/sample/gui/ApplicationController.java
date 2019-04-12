package sample.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import sample.application.Main;
import sample.gui.scraperScreen.ScraperScreenController;


/**
 * @implNote This class is responsible for setting up the functionality of the buttons on the home screen.
 * @implSpec {@link FXML} {@link Button} which aren't utilised currently should realised by the party extending this software.
 */
public class ApplicationController
{
    @FXML private TabPane tab_pane;
    @FXML private Button  button_moodle;
    @FXML private Button button_blue_castle;

    //Functionality to be added in future releases or by a third party extending this software.
    @FXML private Button button_portal;
    @FXML private Button button_grades_modules;
    @FXML private Button button_mynottingham;
    @FXML private Button load_database;
    @FXML private Button button_settings;


    /**
     * This method hooks up functionality to the buttons on the home screen.
     * @implSpec To add behaviour to a new button, specify the button and follow the examples provided, the result should
     * resemble the following {@code my_new_button.setOnAction(e -> Main.getGuiHandler().openTab("My New Special (Id)"));}
     * @implSpec If a new button behaviour is added, then a new {@code case} should be added
     * in the {@link ScraperScreenController}s {@code initialize()} method;
     */
    @FXML private void initialize()
    {
        button_moodle.setOnAction(e -> Main.getGuiHandler().openTab("Moodle (courses)"));
        button_blue_castle.setOnAction(e -> Main.getGuiHandler().openTab("Blue Castle (Grades)"));
    }


    /**
     * Get a reference to the {@link #tab_pane} to allow for the opening and closing of tabs.
     * @return The primary tab pane on the screen.
     */
    @FXML public TabPane getTabPane() { return tab_pane; }
}
