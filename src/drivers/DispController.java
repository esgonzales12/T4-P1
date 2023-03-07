package drivers;

import gui.LedDisplay;
import gui.StateDisplay;
import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;

public class DispController implements DisplayControllerInt {

    private LedDisplay ledDisplay;
    private StateDisplay stateDisplay;
    private String input, prompt;
    public DispController(LedDisplay ledDisplay, StateDisplay stateDisplay) {
        this.ledDisplay = ledDisplay;
        this.stateDisplay = stateDisplay;
    }

    @Override
    public void signal(Color color, StateDisplayType signalType) {
        stateDisplay.display(color, signalType);
    }

    @Override
    public void clear() {
        stateDisplay.off();
        ledDisplay.clearDisplayText();
    }

    @Override
    public void sleep() {
        stateDisplay.off();
        ledDisplay.backlightOff();
    }

    @Override
    public void displayInput(String input) {
        this.input = input;
        ledDisplay.setDisplayText(prompt, input);
    }

    @Override
    public void displayPrompt(String prompt) {
        this.prompt = prompt;
        ledDisplay.setDisplayText(prompt, input);
    }
}

