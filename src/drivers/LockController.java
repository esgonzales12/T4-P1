package drivers;

import gui.LockActuator;

public class LockController implements LockActuator {
    /*
     *code to disengage the lock actuator
     */
private boolean isEngaged = false;
    @Override
    public void disengage() {
        isEngaged = false;
    }

    @Override
    public boolean engaged() {
        return isEngaged;
    }
}

