package sample.gui.tabs;

public interface HomeTab extends Tab
{
    void openSettings();
    void closeSettings();

    TaskTab openTask(String task);
}
