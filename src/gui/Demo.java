package gui;

import administrator.Administrator;
import administrator.AdministratorImpl;
import drivers.*;
import gui.enums.StateDisplayType;
import gui.impl.LedDisplayImpl;
import gui.impl.LockActuatorImpl;
import gui.impl.SafeImpl;
import gui.impl.StateDisplayImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import safe.SafeController;
import safe.SafeControllerImpl;

public class Demo extends Application {
    @Override
    public void start(Stage primaryStage) {
        final double windowWidth = 1200;
        final double windowHeight = 850;


        LedDisplay ledDisplay = new LedDisplayImpl(100,100);
        StateDisplay stateDisplay = new StateDisplayImpl(100,100);
        DisplayControllerInt displayController = new DispController(ledDisplay, stateDisplay);
        Administrator admin = new AdministratorImpl();
        Safe safe = new SafeImpl(windowWidth, windowHeight);
        SafeController safeController = new SafeControllerImpl(admin,displayController);
        KeypadController keypadController = new KeypadControllerImpl(safeController,displayController);
        safe.setKeypadController(keypadController);
        LockActuator actuator = new LockActuatorImpl();
        LockController lockController = new LockController(actuator,safeController);
        UsbDriver usbDriver = new USB.SafeUsbDriver();
        USB usb = new USB(usbDriver,safeController);
        safeController.setKeypadCont(keypadController);
        safeController.setUsb(usbDriver);
        safeController.setLockCont(lockController);
        primaryStage.setScene(new Scene((StackPane) safe, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
