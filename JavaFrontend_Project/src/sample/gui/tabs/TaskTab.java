package sample.gui.tabs;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sample.functionality.forms.formSending.FormSender;
import sample.gui.scraperScreen.ScraperScreenController;

import java.io.IOException;
import java.util.ArrayList;

public class TaskTab extends Tab implements iTaskTab {

    private StackPane background;
    private Pane scraperView;
    private TabPane browserView;
    private Button browserButton;
    private Button scraperButton;
    private boolean isDynamic = false; /*Specify if the website dynamically generates its login form*/
    private ArrayList<String> formTags; /*Convention: [0] = form_name, [1] = username_tag, [2] = password_tag*/
    private FormSender formSender; /*Used to automate the login process*/
    private String taskUrl; /*The URL that the tab is displaying*/


    /**
     * Private constructor, gets called by the public constructors:
     * {@code TaskTab(String id)}, {@code TaskTab(String id, boolean bDynamic)}
     * Note:
     * {@code background}: Generate the background
     * {@code ScraperView}: Generate the stackPane when is in {@code scraperView}
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
     * Public Constructor for {@link TaskTab}
     * @param id Add the id for the tab to load the correct web-page
     */
    public TaskTab(String id) {
        this();
        try {
            setText(id);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    Parent root = loader.load();

                    ScraperScreenController c = loader.getController();
                    c.setId(id);
            switch (id) {
                /* Set a id for the URL, so when the URL needs to be used it can only input the id */
                case "Moodle (courses)":
                    //System.out.println(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    //Parent root = FXMLLoader.load(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    browserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));
                    scraperView.getChildren().add(root);
                    break;
                case "Blue Castle (Grades)":
                    browserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));
                    scraperView.getChildren().add(root);
                    break;
            }
            goToScraperMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Once the Scraper mode button has been activated, it will go to a Scraper mode and allow the developer to edit
     *  This is where the function to scrape the site from {@link FormSender} should be called
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
