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


/**
 * @implSpec This implementation extends {@link Tab}, it is expected that this is true for any future modifications to
 * this class.
 */
public class TaskTab extends Tab implements iTaskTab
{
    private StackPane m_BackgroundStackPane;
    private Pane m_ScraperViewPane;
    private TabPane m_BrowserView;
    private Button m_BrowserButton;
    private Button m_ScraperButton;

    /**
     * @implSpec The {@link Button} to change view defaults to the {@code TOP_RIGHT}.
     * @implNote The Button for browser view and Scraper view {@link #m_BrowserButton}, {@link #m_ScraperButton}
     * respectively can be found here.
     * @implSpec This is a private constructor, it gets called by the public constructors {@link #TaskTab(String)}.
     * {@link #m_BackgroundStackPane}: Generates the {@link StackPane}, which is the background.
     * {@link #m_ScraperViewPane}: Generates the {@link Pane}, which is the current view.
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
     * Public Constructor for {@link TaskTab}.
     * @implSpec This constructor calls the private constructor {@link TaskTab#TaskTab()}.
     * @implNote If new {@link Button}s are to be added to the {@link sample.gui.ApplicationController}, then the relevant
     * behaviour should be specified here, along with a new {@code switch(id)} case for that button.
     * @param id Add the id for the tab to load the correct web-page, identifier for the web-page type.
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
     * @implSpec Once the Scraper mode button is activated, it will go to a Scraper mode, dev can edit the scraper mode
     * appearance.
     * @implSpec This method clears all children from the {@link #m_BackgroundStackPane}.
     */
    @Override
    public void goToScraperMode()
    {
        m_BackgroundStackPane.getChildren().clear();
        m_BackgroundStackPane.getChildren().add(m_ScraperViewPane);
        m_BackgroundStackPane.getChildren().add(m_BrowserButton);
    }


    /**
     * @implSpec Once the Browser mode button is activated, it will go to a Browser mode, the dev can't edit the browser
     * mode appearance.
     * @implSpec This method clears all children from the {@link #m_BackgroundStackPane}.
     */
    @Override
    public void goToBrowserMode()
    {
        m_BackgroundStackPane.getChildren().clear();
        m_BackgroundStackPane.getChildren().add(m_BrowserView);
        m_BackgroundStackPane.getChildren().add(m_ScraperButton);
    }

}
