package sample.application;

import javafx.application.Application;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.gui.GuiHandler;
import sample.gui.iGuiHandler;

/**
 * The Main class serves as the entry point for the application. This class initializes the JavaFX context and
 * displays a window on the screen.
 */
public class Main extends Application
{
    private static TabPane m_TasksPage;
    private static Main m_Self;
    private GuiHandler m_GuiHandler;

    /**
     * @param primaryStage Container for displaying the GUI.
     * @throws Exception if the GUI Handler fails to initialize.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Save a reference to this instance of Main.
        m_Self = this;

        // Set a minimum window size of 400 x 300.
        // The default window size is set by the GUI Handler on startup.
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);

        // Initialize a GUI Handler to do all the heavy lifting.
        m_GuiHandler = new GuiHandler(primaryStage);
    }

    /**
     * Get a reference to the current instance of the GUI handler {@code m_GuiHandler}.
     * @return An instance of GUI handler.
     */
    public static iGuiHandler getGuiHandler() { return m_Self.m_GuiHandler; }


    /**
     * Access the task page of the application {@code m_TaskPage}.
     * @return A reference to the task page.
     */
    public static TabPane getTasksPage() { return m_TasksPage; }


    /**
     * Fallback function to launch the application.
     * @param args Arguments to launch the program with.
     */
    public static void main(String[] args) { launch(args); }

}
