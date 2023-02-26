package gui;

import gui.impl.SafeImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Demo extends Application {
    @Override
    public void start(Stage primaryStage) {
        final double windowWidth = 1200;
        final double windowHeight = 850;
        Safe safe = new SafeImpl(windowWidth, windowHeight);

        primaryStage.setScene(new Scene((StackPane) safe, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
