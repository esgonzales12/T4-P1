package drivers;

import gui.LockActuator;
import safe.SafeController;
import safe.SafeControllerImpl;


public class LockController {
    private LockActuator actuator;
    private SafeController safeController;

    public LockController(LockActuator actuator, SafeController safeController) {
        this.actuator = actuator;
        this.safeController = safeController;
    }

    public void disengage() {
        actuator.disengage();
    }

    public void engaged() {
        boolean isEngaged = actuator.engaged();
       // if (!isEngaged) {
        //    actuator.engaged();
      //  }
    }
}
