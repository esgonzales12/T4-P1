package gui;

import drivers.KeypadController;
import drivers.UsbDriver;
import safe.SafeController;

public interface Safe {
    void setUsbDriver(UsbDriver usbDriver);
    void setKeypadController(KeypadController keypadController);
    LockActuator getLockActuator();
    LedDisplay getStateDisplay();
    StateDisplay getLedDisplay();
    public void setActuatorSafeController(SafeController safeController);
}
