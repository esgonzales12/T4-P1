package drivers;

import gui.LockActuator;

public class LockController implements LockActuator {
    /*
     *code to disengage the lock actuator
     */
private boolean isEngaged = false;
/*
 * method sets the isEngaged field to false,
 * indicating that the lock actuator is disengaged.
 */
    @Override
    public void disengage() {
        isEngaged = false;
    }

    @Override
    public boolean engaged() {
        return isEngaged;
    }
}

