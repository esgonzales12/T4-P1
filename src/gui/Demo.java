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

        Safe safe = new SafeImpl(windowWidth, windowHeight);
        DisplayControllerInt displayController = new DispController(safe.getStateDisplay(), safe.getLedDisplay());
        Administrator admin = new AdministratorImpl();
        SafeController safeController = new SafeControllerImpl(admin,displayController);
        KeypadController keypadController = new KeypadControllerImpl(safeController,displayController);
        safe.setKeypadController(keypadController);
        LockController lockController = new LockController(safe.getLockActuator(),safeController);
        UsbDriver usbDriver = new USB.SafeUsbDriver();
        USB usb = new USB(usbDriver,safeController);
        safeController.setKeypadCont(keypadController);
        safeController.setUsb(usbDriver);
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
