package drivers;

import gui.LedDisplay;
import gui.StateDisplay;
import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;

public class DispController implements DisplayControllerInt {

    private LedDisplay ledDisplay;
    private StateDisplay stateDisplay;

    public DispController() {

    }

    @Override
    public void signal(Color color, StateDisplayType signalType) {
        stateDisplay.display(color, signalType);
    }

    @Override
    public void clear() {

    }

    @Override
    public void sleep() {

    }

    @Override
    public void displayInput(String input) {

    }

    @Override
    public void displayPrompt(String prompt) {

    }
}
