package sample.gui.tabs;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TaskTab extends Tab implements iTaskTab {

    private StackPane background;
    private Pane scraperView;
    private TabPane browserView;
    private Button browserButton;
    private Button scraperButton;

    /**
     * Note:
     * background: Generate the background
     * ScraperView: Generate the stackPane when is inn scraperview
     * Added the button for browser view and Scraper view
     */
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

    /**
     *
     * @param id Add the id for the tab
     */
    public TaskTab(String id) {
        this();
        try {
            setText(id);
            switch (id) {
                /* Set a id for the URL, so when the URL needs to be used it can only input the id */
                case "Moodle (courses)":
                    //System.out.println(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    browserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));
                    scraperView.getChildren().add(root);
                    break;
                case "Blue Castle (Grades)":
                    browserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));
                    break;
            }
            goToBrowserMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Once the Scraper mode button has been activated, it will go to a Scraper mode and allow the developer to edit
     */
    @Override
    public void goToScraperMode() {
        background.getChildren().clear();
        background.getChildren().add(scraperView);
        background.getChildren().add(browserButton);
    }


    /**
     *  Once the Browser mode button has been activated, it will go to a Browser mode and will not allow the developer to edit
     */
    @Override
    public void goToBrowserMode() {
        background.getChildren().clear();
        background.getChildren().add(browserView);
        background.getChildren().add(scraperButton);
    }
}
