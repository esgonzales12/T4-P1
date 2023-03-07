package drivers;
import drivers.enums.Key;
import safe.SafeController;

import java.util.ArrayList;
import java.util.List;
public class KeypadControllerImpl implements KeypadController {

    private List<Key> inputBuffer = new ArrayList<>();
    private SafeController safeController;
    private DispController displayController;
    private InputType inputType;

    public KeypadControllerImpl(SafeController safeController, DispController displayController){
        this.safeController = safeController;
        this.displayController = displayController;
    }

    public KeypadControllerImpl() {

    }

    /*If the user hits ENTER on the keypad then the input buffer
    * will handle A meta-key or non-meta-key depending on the input
    * BACKSPACE key removes the last character from the input buffer.
     */
    @Override
    public void receiveKey(Key key) {
        if (key == Key.ENTER) {
            if (inputType == InputType.META) {
                handleMetaKey(inputBuffer);
            } else {
                handleInputKey(inputBuffer);
            }
            inputBuffer.clear();
        } else if (key == Key.BACKSPACE) {
            if (!inputBuffer.isEmpty()) {
                inputBuffer.remove(inputBuffer.size() - 1);
            }
        } else if (key == Key.USER_MANAGEMENT || key == Key.CHANGE_PASSWORD) {
            inputType = InputType.META;
            inputBuffer.clear();
            inputBuffer.add(key);

        } else {
            inputType = InputType.REGULAR;
            inputBuffer.add(key);
        }
        // Update the display with the current input buffer

    }

    // specification for which input type of input is coming
    // one arg or two, username, password, operation (delete -> D, create -> C)
    @Override
    public void setInputType() {
        inputType = InputType.META;
    }

    private void handleMetaKey(List<Key> inputBuffer) {
        if (inputBuffer.contains(Key.USER_MANAGEMENT)) {
            // Handle user management request
        } else if (inputBuffer.contains(Key.CHANGE_PASSWORD)) {
            // Handle change password request
        }
    }

    private void handleInputKey(List<Key> inputBuffer) {
        // Convert inputBuffer into a String and send it to the SafeController
        StringBuilder stringBuilder = new StringBuilder();
        for (Key key : inputBuffer) {
            stringBuilder.append(key.getValue());
        }
        String input = stringBuilder.toString();
        // Send input to SafeController
        safeController.handleInputRequest(input);
    }

    private enum InputType {
        REGULAR, META
    }
}

