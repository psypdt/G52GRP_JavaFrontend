package sample.gui.scraperScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;

public class TmpMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TEST DATA
        JSONArray j = new JSONArray("[{'type':'button', 'text':'Click me'},{'type':'h1', 'text':'This is a heading'}]");

        // load the FXML file
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ScraperScreenView.fxml"));
        Parent root = loader.load();

        // Call the relevant function in the Controller class
        ScraperScreenController c = loader.getController();
        c.displayTags(j);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
