package sample.gui.startScreen;

import sample.gui.pageShortcut.PageShortcuts;
import java.util.ArrayList;


/*This may need to be a singleton pattern & may need to be threadsafe*/
public class StartscreenModel implements Startscreen
{
    private ArrayList<PageShortcuts> shortcuts; /*Have a list of shortcuts that are displayed*/
}
