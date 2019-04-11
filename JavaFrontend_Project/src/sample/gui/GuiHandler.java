package sample.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.gui.tabs.iTaskTab;
import sample.gui.tabs.TaskTab;
import java.io.IOException;


public class GuiHandler implements iGuiHandler
{
    private TabPane tabPane;

    /**
     * Constructor function for the GUI Handler. Initializes the application with a pre-specified FXML file and
     * displays it on the screen.
     * @param mainStage Graphical context display the home screen in.
     * @throws IOException if the FXML file fails to load.
     */
    public GuiHandler(Stage mainStage) throws IOException
    {
        /*It will show the screen based on the fxml file */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Application.fxml"));
        Parent root = loader.load();

        ApplicationController c = loader.getController();
        tabPane = c.getTabPane();

        /* The window title will be set as Java Front-end */
        mainStage.setTitle("Java Front-end");

        /*The window starts with a default size of 600 x 400 */
        mainStage.setScene(new Scene(root, 600, 400));
        mainStage.show();
    }

    /**
     * Open a new tab and add it to the primary tab pane.
     * @param id Identifier for the tab to create.
     */
    @Override
    public void openTab(String id)
    {
        iTaskTab task = new TaskTab(id);
        tabPane.getTabs().add((Tab)task);
    }
}
