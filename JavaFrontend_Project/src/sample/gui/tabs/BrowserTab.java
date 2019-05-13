package sample.gui.tabs;


import sample.gui.browserScreen.BrowserScreenModel;
import sample.gui.scraperScreen.ScraperscreenModel;
import sample.gui.tabs.iTab;

/**
 * This houses what mode the screen is in (browser/ scraper mode)
 */
public class BrowserTab implements iTab
{
    /**
     * Tells us if the tab will redirect to it's linked site in browser or scraper mode
     */
    private boolean scraperMode = true;
    private ScraperscreenModel linkedSiteScraperMode;
    private BrowserScreenModel linkedSiteBrowserMode;
}
