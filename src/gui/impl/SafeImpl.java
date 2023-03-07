package gui.impl;

import drivers.KeypadController;
import drivers.UsbDriver;
import gui.LedDisplay;
import gui.LockActuator;
import gui.Safe;
import gui.StateDisplay;
import gui.impl.container.AccessModule;
import gui.impl.container.LockingMechanism;
import gui.impl.container.UsbDemo;
import gui.util.LightEffect;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import safe.SafeController;
import safe.SafeControllerImpl;

import static gui.util.SafeUtil.*;

public class SafeImpl extends StackPane implements Safe {
    private final LockingMechanism lockingMechanism;
    private final AccessModule safeAccessModule;
    private final LockActuatorImpl actuator;
    private final SafeHandle handle;
    private final UsbDemo usbDemo;

    public SafeImpl(double windowWidth, double windowHeight) {
        lockingMechanism = new LockingMechanism(DOOR_WIDTH + 150, DOOR_HEIGHT);
        safeAccessModule = new AccessModule();
        actuator = new LockActuatorImpl();
        usbDemo = new UsbDemo(windowWidth, windowHeight);
        handle = new SafeHandle();
        init();
    }

    @Override
    public void setUsbDriver(UsbDriver usbDriver) {
        usbDemo.setUsbDriver(usbDriver);
    }

    @Override
    public LockActuator getLockActuator() {
        return actuator;
    }

    @Override
    public void setKeypadController(KeypadController keypadController) {
        safeAccessModule.setKeypadController(keypadController);
    }
    @Override
    public void setActuatorSafeController(SafeController safeController){
        actuator.setSafeCont(safeController);
    }
    @Override
    public LedDisplay getStateDisplay() {
        return safeAccessModule.getLedDisplay();
    }

    @Override
    public StateDisplay getLedDisplay() {
        return safeAccessModule.getStateDisplay();
    }

    private void init() {
        handle.setLockingMechanism(lockingMechanism);
        handle.setLockActuator(actuator);
        getChildren().addAll(lockingMechanism,
                getSafeDoor(),
                usbDemo,
                getEntryComponents());
        setAlignment(Pos.CENTER);
    }

    private Rectangle getSafeDoor() {
        Rectangle door = new Rectangle(DOOR_WIDTH, DOOR_HEIGHT, SAFE_DOOR_COLOR);
        door.setEffect(LightEffect.getLighting());
        door.setArcWidth(20);
        door.setArcHeight(20);
        return door;
    }

    private VBox getEntryComponents() {
        VBox keypadAndHandle = new VBox();
        keypadAndHandle.setEffect(LightEffect.getLighting());
        keypadAndHandle.setAlignment(Pos.TOP_CENTER);
        keypadAndHandle.getChildren().addAll(safeAccessModule, handle);
        keypadAndHandle.setSpacing(10);
        keypadAndHandle.setMaxHeight(700);
        keypadAndHandle.setPickOnBounds(false);
        return  keypadAndHandle;
    }

}
