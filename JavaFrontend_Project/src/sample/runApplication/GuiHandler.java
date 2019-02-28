package sample.runApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.gui.tabs.iTaskTab;
import sample.gui.tabs.TaskTab;

import java.io.IOException;

public class GuiHandler implements iGuiHandler {

    private TabPane tabPane;

    public GuiHandler(Stage mainStage) throws IOException {
        /**
         *  It will show the screen with the fxml file
         *  */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToolBar.fxml"));
        Parent root = loader.load();

        ToolBarController c = loader.getController();
        tabPane = c.getTabPane();
        /**
         *  The window title will be set as Java Front-end
         *  */
        mainStage.setTitle("Java Front-end");
        /**
         *  The window starts with a default size of 600 x 400
         *  */
        mainStage.setScene(new Scene(root, 600, 400));
        mainStage.show();
    }

    @Override
    /**
     *  Will open a new tap when it was activated
     *  */
    public void openTab(String id) {
        iTaskTab task = new TaskTab(id);
        tabPane.getTabs().add((Tab)task);
    }
}
