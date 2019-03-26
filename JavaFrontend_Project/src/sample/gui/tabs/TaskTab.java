package sample.gui.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sample.functionality.forms.formSending.FormSender;

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
        formTags = new ArrayList<>(); /*Array list containing tags needed for a static form*/
        setText(id);
        initialiseTabUrl(id);
        goToBrowserMode();
    }


    /**
     * Overloaded constructor for {@link TaskTab}, can specify if the login form is created dynamically
     * Useful for {@link FormSender} when choosing login method
     * @param id Identification of what tab will be opened, Moodle, BlueCastle, etc.
     * @param bDynamic Disambiguate if the web site dynamically generates the form to log the user in
     */
    public TaskTab(String id, boolean bDynamic)
    {
        this();
        isDynamic = bDynamic;
        formTags = new ArrayList<>(); /*Array list containing tags needed for a static form*/
        setText(id);
        initialiseTabUrl(id);
        goToBrowserMode();
    }


    /**
     * This method will setup the URL for the tab and the {@code formTags} list
     * NOTE: Additional initialisation behaviour/properties (Auto-login, form-tags) for websites should be done here
     * {@code formSender} is instantiated in this method
     * @param id Identifier for what website will be loaded
     */
    private void initialiseTabUrl(String id)
    {
        switch (id) {
            /* Set a id for the URL, so when the URL needs to be used it can only input the id, set relevant form tags*/
            case "Moodle (courses)":
//                browserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));
                taskUrl = "https://moodle.nottingham.ac.uk";
                formTags.add("#login");
                formTags.add("#username");
                formTags.add("#password");
                break;
            case "Blue Castle (Grades)":
//                browserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));
                taskUrl = "https://bluecastle-results.nottingham.ac.uk/login";
                formTags.add("form");
                formTags.add("#UserName");
                formTags.add("#Password");
                break;
            case "MyNottingham":
//                browserView.getTabs().add(new WebViewTab("http://mynottingham.nottingham.ac.uk"));
                taskUrl = "http://mynottingham.nottingham.ac.uk";
                formTags.add("form#login");
                formTags.add("#userid");
                formTags.add("#pwd");
                break;
        }
        formSender = new FormSender(taskUrl); /*This logs the user in, but need to find a way to pass correct url back*/

        /*
        * This displays the incorrect page due to the async method taking longer, displayed url is outdated
        * This needs to be resolved in the FormSender class
        */
        browserView.getTabs().add(new WebViewTab(formSender.getWebView().getEngine().getLocation()));
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


    /**
     * Getter for {@code formTags}, will be used by {@link FormSender} to get necessary login-form tags
     * @return {@code formTags} The list containing tags required to find the login form for a website
     */
    public ArrayList<String> getFormTags()
    {
        return formTags;
    }


    /**
     * Getter for {@code isDynamic}, will be used by {@link FormSender} to choose the appropriate login method
     * @return {@code isDynamic}
     */
    public boolean isDynamic()
    {
        return isDynamic;
    }


    /**
     * Getter for the {@code taskUrl}, the url that the {@link TaskTab} is displaying
     * @return {@code taskUrl}
     */
    public String getTaskUrl()
    {
        return taskUrl;
    }
}
