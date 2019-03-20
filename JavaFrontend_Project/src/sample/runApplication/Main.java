package sample.runApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.functionality.jSoupParsing.Parser;


public class Main extends Application
{
    private static Stage mainStage;
    private static Pane main_pane;
    private static TabPane tasksPage;
    private static ScrollPane startPage;

    private static Main self;
    private GuiHandler guiHandler;

    /**
     * This function allows us to get the main stage from anywhere by calling Main.getStage()
     * @return mainStage: return to the new page that has been generated
     */
    public static Stage getStage()
    {
        return mainStage;
    }

    /**
     * @param primaryStage: What the screen going to be look like at the begining
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        self = this;
        mainStage = primaryStage;

        /**
         * set a minimum window size of 300 x 200
         * The guiHandler will makes the the window opening in a fixed size every time when it was opened
         */
        mainStage.setMinWidth(300);
        mainStage.setMinHeight(200);

        //initStartPage();
        //initTasksPage();

        guiHandler = new GuiHandler(primaryStage);
    }

    /**
     *
     * @return guiHandler: Return to the guihandler
     */
    public static iGuiHandler getApp() { return self.guiHandler; }


    /**
     *
     * @return taskPage: Return to the taskPage
     */
    public static TabPane getTasksPage() { return tasksPage; }


    /**
     * @param args: To launch the program
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
