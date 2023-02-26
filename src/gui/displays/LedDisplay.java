package gui.displays;

public interface LedDisplay {
    void backlightOn();
    void backlightOff();
    void setDisplayText(String prompt, String input);
    void clearDisplayText();
}
