package drivers;

import drivers.enums.Key;

public interface KeypadController {
    void receiveKey(Key key);
    void setInputType();
}
