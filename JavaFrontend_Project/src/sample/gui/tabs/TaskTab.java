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


public class TaskTab extends Tab implements iTaskTab
{
    private StackPane m_BackgroundStackPane;
    private Pane m_ScraperViewPane;
    private TabPane m_BrowserView;
    private Button m_BrowserButton;
    private Button m_ScraperButton;

    /**
     * Private constructor, gets called by the public constructors {@code TaskTab(String id)}
     * {@code m_BackgroundStackPane}: Generates the Background
     * {@code ScraperView}: Generates the stackPane when its is in {@code m_ScraperViewPane}
     * The Button for browser view and Scraper view can be found here
     */
    private TaskTab()
    {
        super();

        m_BackgroundStackPane = new StackPane();
        m_BackgroundStackPane.setAlignment(Pos.TOP_RIGHT);

        m_ScraperViewPane = new StackPane();

        m_BrowserView = new TabPane();
        m_BrowserView.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        m_BrowserButton = new Button("Go to browser view");
        m_BrowserButton.setOnAction(e -> goToBrowserMode());

        m_ScraperButton = new Button("Go to scraper view");
        m_ScraperButton.setOnAction(e -> goToScraperMode());

        this.setContent(m_BackgroundStackPane);
    }


    /**
     * @implNote This constructor calls the private constructor {@code TaskTab()}
     * Public Constructor for {@link TaskTab}, this calls the private constructor for {@link TaskTab}
     * @param id Add the id for the tab to load the correct web-page, identifier for the web-page type
     */
    public TaskTab(String id)
    {
        this(); //Call private constructor

        try
        {
            setText(id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
            Parent root = loader.load();

            ScraperScreenController scraperScreenController = loader.getController();
            scraperScreenController.setId(id);

            switch (id)
            {
                /* Set a id for the URL, so when the URL needs to be used it can only input the id */
                case "Moodle (courses)":
                {
                    //System.out.println(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    //Parent root = FXMLLoader.load(getClass().getResource("/sample/gui/scraperScreen/ScraperscreenView.fxml"));
                    m_BrowserView.getTabs().add(new WebViewTab("https://moodle.nottingham.ac.uk"));
                    m_ScraperViewPane.getChildren().add(root);
                    break;
                }
                case "Blue Castle (Grades)":
                {
                    m_BrowserView.getTabs().add(new WebViewTab("https://bluecastle.nottingham.ac.uk"));
                    m_ScraperViewPane.getChildren().add(root);
                    break;
                }
            }
            goToScraperMode(); //Display ScraperMode by default, remove to display the original website by default

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Once the Scraper mode button has been activated, it will go to a Scraper mode and allow the developer to edit
     *  This is where the function to scrape the site from {@link FormSender} should be called
     */
    @Override
    public void goToScraperMode()
    {
        m_BackgroundStackPane.getChildren().clear();
        m_BackgroundStackPane.getChildren().add(m_ScraperViewPane);
        m_BackgroundStackPane.getChildren().add(m_BrowserButton);
    }


    /**
     *  Once the Browser mode button has been activated, it will go to a Browser mode and will not allow the developer to edit
     */
    @Override
    public void goToBrowserMode()
    {
        m_BackgroundStackPane.getChildren().clear();
        m_BackgroundStackPane.getChildren().add(m_BrowserView);
        m_BackgroundStackPane.getChildren().add(m_ScraperButton);
    }

}
