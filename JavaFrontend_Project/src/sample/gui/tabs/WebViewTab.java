package sample.gui.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import sample.Main;

public class WebViewTab extends Tab
{
    private WebView webpage;

    public WebViewTab(String url)
    {
        super();
        webpage = new WebView();

        setContent(webpage);

        webpage.getEngine().load(url);

        /* load() is an async operation. Add a event listener for the SUCCEEDED state for code that's supposed to run
           after it's finished */
        // SOURCE: http://www.java2s.com/Tutorials/Java/JavaFX/1500__JavaFX_WebEngine.htm#Obtain_the_raw_XML_data_as_a_string_of_text
        webpage.getEngine().getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) ->
        {
            if (newValue == Worker.State.SUCCEEDED)
            {
                setText(webpage.getEngine().getTitle());
                Main.goToTasksPage();
            }
        });

    }
}
