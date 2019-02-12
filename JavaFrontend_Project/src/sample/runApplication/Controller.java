package sample.runApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import sample.runApplication.Main;

/*This is responsible for controlling the Tool bar, aka. fixed "Tasks" & "Start" buttons*/
public class Controller
{
    @FXML
    private Pane main_pane;
    @FXML private Button start_button;
    @FXML private Button tasks_button;

    @FXML private void initialize()
    {
        Main.setMainPane(main_pane);
        /* Append the start page to the app */
        Main.goToStartPage();

        /* Set up the buttons to switch to their respective panes */
        start_button.setOnAction(e -> Main.goToStartPage());
        tasks_button.setOnAction(e -> Main.goToTasksPage());
    }
}
