package drivers;

import gui.LockActuator;
import safe.SafeControllerImpl;

public class LockController {
    private LockActuator actuator;
    private SafeControllerImpl safeController;
    private boolean isLocked;

    public LockController(LockActuator actuator, SafeControllerImpl safeController) {
        this.actuator = actuator;
        this.safeController = safeController;
        this.isLocked = true;
    }
    /* disengages the lock
     */
    public void disengage() {
        if (isLocked) {
            actuator.disengage();
            isLocked = false;
        }
    }
    /*method is called to lock the lock
     */
    public void engage() {
        if (!isLocked) {
            actuator.disengage();
            isLocked = true;
        }
    }
/*
    public void update() {
        if (safeController.isUnlocked() && isLocked) {
            disengage();
        } else if (safeController.isLocked() && !isLocked) {
            engage();
        }
    }

 */
}

