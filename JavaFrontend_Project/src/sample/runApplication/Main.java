package sample.runApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.functionality.webpage.Parser;

import java.io.IOException;


public class Main extends Application
{
    private static Stage mainStage;
    private static Pane main_pane;
    private static TabPane tasksPage;
    private static AnchorPane startPage;

    /*This function allows us to get the main stage from anywhere by calling Main.getStage()*/
    public static Stage getStage()
    {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainStage = primaryStage;

        initStartPage();
        initTasksPage();

        Parent root = FXMLLoader.load(getClass().getResource("ToolBar.fxml"));
        primaryStage.setTitle("Java Front-end");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void initStartPage() throws Exception
    {
        /*Works when wanting to load alternative view*/
//        startPage = FXMLLoader.load(getClass().getResource("/sample/gui/startScreen/StartScreenView.fxml"));
        startPage = FXMLLoader.load(getClass().getResource("StartScreenView.fxml"));
    }

    public static void goToStartPage()
    {
        main_pane.getChildren().clear();
        main_pane.getChildren().add(startPage);
    }

    private void initTasksPage() throws Exception
    {
        tasksPage = FXMLLoader.load(getClass().getResource("TaskView.fxml"));
    }

    public static TabPane getTasksPage() { return tasksPage; }

    public static void goToTasksPage()
    {
        main_pane.getChildren().clear();
        main_pane.getChildren().add(tasksPage);

        Thread t1 = new Thread(new Parser());
        t1.start();
        /*Temporary, create parser, should be moved into a controller in future. */
//        Parser parser = new Parser();
//        try
//        {
//            parser.parseSpecificTag("input", "https://moodle.nottingham.ac.uk/login/index.php");
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }

    public static void setMainPane(Pane p)
    {
        main_pane = p;
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
