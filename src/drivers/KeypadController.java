package drivers;

import drivers.enums.Key;

public interface KeypadController {
    void receiveKey(Key key);
    //void setInputType();
    void setExpectedInputLength(int expectedMin, int expectedMax, boolean password);
}
