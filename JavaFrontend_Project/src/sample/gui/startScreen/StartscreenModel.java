package sample.gui.startScreen;

import sample.gui.browserTab.BrowserTab;
import sample.gui.pageShortcut.PageShortcuts;
import java.util.ArrayList;


/*This may need to be a singleton pattern & may need to be threadsafe*/
public class StartscreenModel implements Startscreen
{
    private ArrayList<PageShortcuts> shortcuts; /*Have a list of shortcuts that are displayed*/


    @Override
    public void openNewTab(BrowserTab tabObject)
    {

    }

    @Override
    public void changeSettings()
    {

    }



    public ArrayList<PageShortcuts> getShortcuts()
    {
        return shortcuts;
    }

    public void setShortcuts(ArrayList<PageShortcuts> shortcuts)
    {
        this.shortcuts = shortcuts;
    }


}
