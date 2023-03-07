package drivers;

import gui.LockActuator;
import safe.SafeControllerImpl;


public class LockController {
    private LockActuator actuator;
    private SafeControllerImpl safeController;

    public LockController(LockActuator actuator) {
        this.actuator = actuator;
        this.safeController = new SafeControllerImpl();
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
