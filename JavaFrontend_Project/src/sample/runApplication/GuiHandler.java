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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToolBar.fxml"));
        Parent root = loader.load();

        ToolBarController c = loader.getController();
        tabPane = c.getTabPane();

        mainStage.setTitle("Java Front-end");
        mainStage.setScene(new Scene(root, 600, 400));
        mainStage.show();
    }

    @Override
    public void openTab(String id) {
        iTaskTab task = new TaskTab(id);
        tabPane.getTabs().add((Tab)task);
    }
}
