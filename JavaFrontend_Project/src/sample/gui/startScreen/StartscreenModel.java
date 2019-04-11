package sample.gui.startScreen;

import sample.gui.tabs.BrowserTab;
import sample.gui.pageShortcut.PageShortcuts;
import java.util.ArrayList;

/**
 * CAN THIS CLASS BE REMOVED? DOESN'T SEEM TO BE USED ANYWHERE
 */

/*This may need to be a singleton pattern & may need to be threadsafe*/
public class StartscreenModel implements sample.gui.startScreen.Startscreen
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
