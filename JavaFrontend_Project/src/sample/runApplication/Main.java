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
     * @return
     */
    public static Stage getStage()
    {
        return mainStage;
    }

    /**
     * What the screen going to be look like at the begining
     * @param primaryStage
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


    public static iGuiHandler getApp() { return self.guiHandler; }

    /***
     * This method initialises the StartScreenView, calls fxml that will be displayed
     * @throws Exception This throws an Exception due to the FXMLLoader.load() method
     */
//    private void initStartPage() throws Exception
//    {
//        /*Works when wanting to load alternative view*/
////        startPage = FXMLLoader.load(getClass().getResource("/sample/gui/startScreen/StartScreenView.fxml"));
//        //startPage = FXMLLoader.load(getClass().getResource("StartScreenView.fxml"));
//    }


//    public static void goToStartPage()
//    {
//        main_pane.getChildren().clear();
//        main_pane.getChildren().add(startPage);
//    }
//
//    private void initTasksPage() throws Exception
//    {
//        tasksPage = FXMLLoader.load(getClass().getResource("TaskView.fxml"));
//    }


    public static TabPane getTasksPage() { return tasksPage; }


//    public static void goToTasksPage()
//    {
//        main_pane.getChildren().clear();
//        main_pane.getChildren().add(tasksPage);
//
//        /*Create parser, should be moved into a controller in future so that url can be extracted from there*/
//        Parser parser = new Parser();
//        parser.parseSpecificTag("input", "https://moodle.nottingham.ac.uk/login/index.php");
//    }
//
//    public static void setMainPane(Pane p)
//    {
//        main_pane = p;
//    }

    /**
     * To launch the pogram
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
