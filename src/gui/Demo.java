package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final double windowWidth = 1200;
        final double windowHeight = 850;
        SafeDoor safeDoor = new SafeDoor(windowWidth, windowHeight);
        primaryStage.setScene(new Scene(safeDoor, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
