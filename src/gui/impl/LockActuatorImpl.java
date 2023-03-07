package gui.impl;

import gui.LockActuator;
import log.StaticLogBase;
import safe.SafeController;
import safe.enums.State;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class LockActuatorImpl extends StaticLogBase implements LockActuator {
    private final AtomicBoolean engaged;
    private static SafeController safeCont;
    public LockActuatorImpl() {
        engaged = new AtomicBoolean(true);
        log.setLevel(Level.OFF);
    }
    public void setSafeCont(SafeController safeController){
        safeCont = safeController;
    }
    public boolean engaged() {
        return engaged.get();
    }

    public synchronized void disengage() {
        log.info("DISENGAGED");
        engaged.set(false);
        notifyAll();
    }

    protected void engage() {
        log.info("ENGAGED");
        engaged.set(true);
        safeCont.handleStateChangeRequest(State.LOCKED);
    }

}
