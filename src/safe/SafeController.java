package safe;

import safe.enums.State;

public interface SafeController {
    boolean handleStateChangeRequest(State state);
    void handleInputRequest(String input);

}
