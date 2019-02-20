package sample.gui.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TaskTab extends Tab implements iTaskTab {

    private StackPane background;
    private Pane scraperView;
    private TabPane browserView;

    private TaskTab() {
        super();

        background = new StackPane();
        scraperView = new StackPane();
        browserView = new TabPane();

        this.setContent(background);
    }

    public TaskTab(String id) {
        this();
        setText(id);
    }

    @Override
    public void goToScraperMode() {
        background.getChildren().clear();
        background.getChildren().add(scraperView);
    }

    @Override
    public void goToBrowserMode() {
        background.getChildren().clear();
        background.getChildren().add(browserView);
    }
}
