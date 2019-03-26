package sample.gui.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;


public class TaskTab extends Tab implements iTaskTab {

    private StackPane background;
    private Pane scraperView;
    private TabPane browserView;
    private Button browserButton;
    private Button scraperButton;
    private boolean isDynamic = false; /*Specify if the website dynamically generates its login form*/
    private ArrayList<String> formTags;

    /**
     * Private constructor, gets called by the public constructors
     * Note:
     * background: Generate the background
     * ScraperView: Generate the stackPane when is in scraperview
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
     * Public Constructor
     * @param id Add the id for the tab
     */
    public TaskTab(String id) {
        this();
        formTags = new ArrayList<>(); /*Array list containing tags needed for a static form*/
        setText(id);
        setTabUrl(id);
        goToBrowserMode();
    }


    /**
     * Overloaded constructor, can specify if the login form is created dynamically,
     * useful for {@link sample.functionality.forms.formSending.FormSender}
     * @param id Identification of what tab will be opened, Moodle, BlueCastle etc.
     * @param bDynamic Does the web site use a dynamically generated from to log the user in
     */
    public TaskTab(String id, boolean bDynamic)
    {
        this();
        isDynamic = bDynamic;
        formTags = new ArrayList<>(); /*Array list containing tags needed for a static form*/
        setText(id);
        setTabUrl(id);
        goToBrowserMode();
    }


    /**
     * This method will setup the URL for the tab and the {@code formTags} list
     * NOTE: Additional initialisation behaviour/properties for websites should be added here
     * @param id Identifier for what website will be loaded
     */
    private void setTabUrl(String id)
    {
        switch (id) {
            /* Set a id for the URL, so when the URL needs to be used it can only input the id, set relevant form tags*/
            case "Moodle (courses)":
                browserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));

                formTags.add("#login");
                formTags.add("#username");
                formTags.add("#password");
                break;
            case "Blue Castle (Grades)":
                browserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));

                formTags.add("form");
                formTags.add("#UserName");
                formTags.add("#Password");
                break;
            case "MyNottingham":
                browserView.getTabs().add(new WebViewTab("http://mynottingham.nottingham.ac.uk"));

                formTags.add("form#login");
                formTags.add("#userid");
                formTags.add("#pwd");
                break;
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


    /**
     * Getter for {@code formTags}, which is a list of tags needed by the login form for respective websites
     * @return {@code formTags} The list containing tags required to find the login form for a website
     */
    public ArrayList<String> getFormTags() {
        return formTags;
    }

    /**
     * Getter to check if {@code isDynamic} flag
     * @return {@code isDynamic}
     */
    public boolean isDynamic() {
        return isDynamic;
    }
}
