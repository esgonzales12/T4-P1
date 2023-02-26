package gui.impl;

import gui.LockActuator;
import log.StaticLogBase;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class LockActuatorImpl extends StaticLogBase implements LockActuator {
    private final AtomicBoolean engaged;

    public LockActuatorImpl() {
        engaged = new AtomicBoolean(true);
        log.setLevel(Level.OFF);
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
    }

}
