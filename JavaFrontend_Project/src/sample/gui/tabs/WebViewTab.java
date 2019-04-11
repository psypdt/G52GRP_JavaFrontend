package sample.gui.tabs;

import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

/**
 * @implNote There is currently no means of returning to the previous instant of a page (no back button)
 */
public class WebViewTab extends Tab implements iBrowserTab
{
    private WebView m_WebPage;

    /**
     * Constructor for {@link WebViewTab}, will load the tab into the webview
     * @param url The URL that {@link org.jsoup.Jsoup} will connect to
     */
    public WebViewTab(String url)
    {
        super();
        m_WebPage = new WebView();

        setContent(m_WebPage);

        m_WebPage.getEngine().load(url);

        /* load() is an async operation. Add a event listener for the SUCCEEDED state for code that's supposed to run
           after it's finished */
        // SOURCE: http://www.java2s.com/Tutorials/Java/JavaFX/1500__JavaFX_WebEngine.htm#Obtain_the_raw_XML_data_as_a_string_of_text
        m_WebPage.getEngine().getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            if (newValue == Worker.State.SUCCEEDED)
            {
                setText(m_WebPage.getEngine().getTitle());
                //Main.goToTasksPage();
            }
        });
    }

    /**
     * @implNote This method has not been implemented, it is up to the developer whether the user can disable functionality
     */
    @Override
    public void disableFunctionality() {}
}
