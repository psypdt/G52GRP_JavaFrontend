package sample.gui.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TaskTab extends Tab implements iTaskTab {

    private StackPane background;
    private Pane scraperView;
    private TabPane browserView;
    private Button browserButton;
    private Button scraperButton;

    private TaskTab() {
        super();

        background = new StackPane();
        background.setAlignment(Pos.TOP_RIGHT);

        scraperView = new StackPane();

        browserView = new TabPane();
        browserView.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        browserButton = new Button("Go to browser view");
        browserButton.setOnAction(e -> goToBrowserMode());

        scraperButton = new Button("Go to scraper view");
        scraperButton.setOnAction(e -> goToScraperMode());

        this.setContent(background);
    }

    public TaskTab(String id) {
        this();
        setText(id);
        switch (id) {
            case "Moodle (courses)":
                browserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));
                break;
            case "Blue Castle (Grades)":
                browserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));
                break;
        }
        goToBrowserMode();
    }

    @Override
    public void goToScraperMode() {
        background.getChildren().clear();
        background.getChildren().add(scraperView);
        background.getChildren().add(browserButton);
    }

    @Override
    public void goToBrowserMode() {
        background.getChildren().clear();
        background.getChildren().add(browserView);
        background.getChildren().add(scraperButton);
    }
}
