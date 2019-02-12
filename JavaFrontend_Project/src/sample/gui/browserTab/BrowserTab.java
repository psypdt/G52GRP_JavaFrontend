package sample.gui.browserTab;


import sample.gui.browserScreen.BrowserScreenModel;
import sample.gui.scraperScreen.ScraperscreenModel;
import sample.gui.tabs.Tab;

/*
* This houses what mode the screen is in (browser/ scraper mode)
*/
public class BrowserTab extends Tab
{
    private boolean scraperMode = true; /*Tells us if the tab will redirect to it's linked site in browser or scraper mode*/
    private ScraperscreenModel linkedSiteScraperMode;
    private BrowserScreenModel linkedSiteBrowserMode;
}
