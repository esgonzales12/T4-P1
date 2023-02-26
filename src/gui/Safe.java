package gui;

import drivers.KeypadController;

public interface Safe {
    void setUsbDriver();
    void setLockController();
    void setKeypadController(KeypadController keypadController);
    void setStateDisplayDriver();
    void setLedDisplayDriver();
}
