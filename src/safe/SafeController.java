package safe;

import drivers.KeypadController;
import drivers.LockController;
import drivers.UsbDriver;
import safe.enums.State;

public interface SafeController {
    boolean handleStateChangeRequest(State state);
    void handleInputRequest(String input);
    public void setUsb(UsbDriver usbIn);
    public void setLockCont(LockController lockController);
    public void setKeypadCont(KeypadController keypadController);

}
