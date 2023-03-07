package drivers;

import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;

public interface DisplayControllerInt {
    void signal(Color color, StateDisplayType signalType);
    void clear();
    void sleep();
    void displayInput(String input);
    void displayPrompt(String prompt);

}
