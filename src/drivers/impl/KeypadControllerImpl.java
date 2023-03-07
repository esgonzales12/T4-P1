package drivers.impl;
import drivers.DisplayController;
import drivers.KeypadController;
import drivers.TimeKeeper;
import drivers.enums.Key;
import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;
import safe.SafeController;
import safe.enums.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KeypadControllerImpl implements KeypadController, TimeKeeper {

    private List<Key> inputBuffer = new ArrayList<>();
    private SafeController safeController;
    private DisplayController displayController;
    //private InputType inputType;
    private int[] expectedInputLength = {4,4};
    private boolean password;
    private Timer timer;
    private Executor executor;

    public KeypadControllerImpl(SafeController safeController, DisplayController displayController){
        this.safeController = safeController;
        this.displayController = displayController;
        this.executor = Executors.newSingleThreadExecutor();
        this.timer = new Timer();
        this.timer.setTimeKeeper(this);
        this.executor.execute(timer);
    }

    public KeypadControllerImpl() {

    }

    public void off() {
        timer.end();
    }

    /*If the user hits ENTER on the keypad then the input buffer
    * will handle A meta-key or non-meta-key depending on the input
    * BACKSPACE key removes the last character from the input buffer.
     */
    @Override
    public void receiveKey(Key key) {
        timer.startTimer();
        if (key == Key.ENTER) {
            if (inputBuffer.size() >= expectedInputLength[0] && inputBuffer.size() <= expectedInputLength[1]) {
                handleInputKey(inputBuffer);
            } else {
                //flash red
                displayController.signal(Color.RED, StateDisplayType.FLASH);
                //display invalid length
            }
            inputBuffer.clear();
        } else if (key == Key.BACKSPACE) {
            if (!inputBuffer.isEmpty()) {
                inputBuffer.remove(inputBuffer.size() - 1);
                //flash yellow
                displayController.signal(Color.YELLOW, StateDisplayType.FLASH);
            } else {
                // flash red
                displayController.signal(Color.RED, StateDisplayType.FLASH);
            }
        } else if (key == Key.USER_MANAGEMENT || key == Key.CHANGE_PASSWORD) {
            //inputType = InputType.META;
            inputBuffer.clear();
            inputBuffer.add(key);
            handleMetaKey(inputBuffer);
            inputBuffer.clear();

        } else {
            //inputType = InputType.REGULAR;
            inputBuffer.add(key);
        }
        // Update the display with the current input buffer
        displayController.displayInput(makeString(inputBuffer, password));


    }

    // specification for which input type of input is coming
    // one arg or two, username, password, operation (delete -> D, create -> C)
    /*
    @Override
    public void setInputType() {
        inputType = InputType.META;
    }
    */
    private void handleMetaKey(List<Key> inputBuffer) {
        if (inputBuffer.contains(Key.USER_MANAGEMENT)) {
            // Handle user management request
            safeController.handleStateChangeRequest(State.ADMIN);
        } else if (inputBuffer.contains(Key.CHANGE_PASSWORD)) {
            // Handle change password request
            safeController.handleStateChangeRequest(State.CHANGEPWD);
        }
    }

    private void handleInputKey(List<Key> inputBuffer) {
        // Convert inputBuffer into a String and send it to the SafeController
        String input = makeString(inputBuffer, false);
        // Send input to SafeController
        safeController.handleInputRequest(input);
    }

    public void setExpectedInputLength(int expectedMin, int expectedMax, boolean password){
        expectedInputLength[0] = expectedMin;
        expectedInputLength[1] = expectedMax;
        this.password = password;
    }

    private String makeString(List<Key> inputBuffer, boolean asterisk){
        StringBuilder stringBuilder = new StringBuilder();
        if(!asterisk){
            for (Key key : inputBuffer) {
                stringBuilder.append(key.getValue());
            }
        } else {
            for (Key key : inputBuffer) {
                stringBuilder.append('*');
            }
        }
        String input = stringBuilder.toString();
        return input;
    }

    @Override
    public void notifyTimeout() {
        inputBuffer.clear();
        displayController.clear();
        displayController.sleep();
    }

    private enum InputType {
        REGULAR, META
    }
}

