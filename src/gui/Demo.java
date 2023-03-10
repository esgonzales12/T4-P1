package gui;

import administrator.Administrator;
import administrator.AdministratorImpl;
import drivers.*;
import drivers.impl.DisplayControllerImpl;
import drivers.impl.KeypadControllerImpl;
import drivers.impl.LockController;
import drivers.impl.UsbDriverImpl;
import gui.impl.SafeImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import safe.SafeController;
import safe.SafeControllerImpl;

public class Demo extends Application {
    @Override
    public void start(Stage primaryStage) {
        final double windowWidth = 1200;
        final double windowHeight = 850;

        Safe safe = new SafeImpl(windowWidth, windowHeight);
        DisplayController displayController = new DisplayControllerImpl(safe.getStateDisplay(), safe.getLedDisplay());
        Administrator admin = new AdministratorImpl();
        SafeController safeController = new SafeControllerImpl(admin,displayController);
        KeypadController keypadController = new KeypadControllerImpl(safeController,displayController);
        safe.setKeypadController(keypadController);
        LockController lockController = new LockController(safe.getLockActuator(),safeController);
        UsbDriver usb = new UsbDriverImpl(safeController);
        safe.setUsbDriver(usb);
        safeController.setKeypadCont(keypadController);
        safeController.setUsb(usb);
        safeController.setLockCont(lockController);
        safe.setActuatorSafeController(safeController);
        primaryStage.setScene(new Scene((StackPane) safe, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
