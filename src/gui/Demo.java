package gui;

import drivers.KeypadControllerImpl;
import drivers.LockController;
import gui.impl.LockActuatorImpl;
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
        SafeController safeController = new SafeControllerImpl();
        safe.setKeypadController(new KeypadControllerImpl());

        LockActuator actuator = new LockActuatorImpl();
//        LockController lockController = new LockController();



        primaryStage.setScene(new Scene((StackPane) safe, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
