package sample.gui.browsertab;


import sample.gui.browserscreen.BrowserScreenController;
import sample.gui.browserscreen.BrowserScreenModel;
import sample.gui.scraperscreen.ScraperscreenModel;

/*
* This houses what mode the screen is in (browser/ scraper mode)
*/
public class BrowserTab
{
    private boolean scraperMode = true; /*Tells us if the tab will redirect to it's linked site in browser or scraper mode*/
    private ScraperscreenModel linkedSiteScraperMode;
    private BrowserScreenModel linkedSiteBrowserMode;
}
