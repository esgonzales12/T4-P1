package gui;

import drivers.KeypadController;
import drivers.UsbDriver;

public interface Safe {
    void setUsbDriver(UsbDriver usbDriver);
    void setKeypadController(KeypadController keypadController);
    LockActuator getLockActuator();
    LedDisplay getStateDisplay();
    StateDisplay getLedDisplay();
}
