package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Main extends Application
{
    private static Pane main_pane;
    private static TilePane startPage;
    private static TabPane tasksPage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        initStartPage();
        initTasksPage();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Java Front-end");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private void initStartPage() throws Exception
    {
        startPage = FXMLLoader.load(getClass().getResource("StartScreenView.fxml"));
    }

    static void goToStartPage()
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
