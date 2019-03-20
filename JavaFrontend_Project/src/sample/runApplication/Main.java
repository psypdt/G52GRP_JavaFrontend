package sample.runApplication;

import javafx.application.Application;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the application. This class initializes the JavaFX context and
 * displays a window on the screen.
 */
public class Main extends Application
{
    private static TabPane tasksPage;

    private static Main self;

    private GuiHandler guiHandler;

    /**
     * @param primaryStage Container for displaying the GUI.
     * @throws Exception if the GUI Handler fails to initialize.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Save a reference to this instance of Main
        self = this;

        // Set a minimum window size of 300 x 200.
        // The default window size is set by the GUI Handler on startup.
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);

        // Initialize a GUI Handler to do all the heavy lifting
        guiHandler = new GuiHandler(primaryStage);
    }

    /**
     * Get a reference to the current instance of the GUI handler.
     * @return An instance of GUI handler.
     */
    public static iGuiHandler getGuiHandler() { return self.guiHandler; }


    /**
     * Access the task page of the application.
     * @return A reference to the task page.
     */
    public static TabPane getTasksPage() { return tasksPage; }


    /**
     * Fallback function to launch the application.
     * @param args Arguments to launch the program with
     */
    public static void main(String[] args) { launch(args); }

}
