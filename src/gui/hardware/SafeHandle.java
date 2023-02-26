package gui.hardware;

import gui.LightEffect;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class SafeHandle extends Pane {

    private static final double WINDOW_WIDTH = 100;
    private static final double WINDOW_HEIGHT = 100;
    private static final int CIRCLE_RADIUS = 50;
    private static final int RECTANGLE_WIDTH = 75;
    private static final int RECTANGLE_HEIGHT = 250;
    private static final Duration ROTATION_DURATION = Duration.seconds(0.5);
    private int rotationDeg = -60;

    public SafeHandle() {

        Circle circle = new Circle(CIRCLE_RADIUS, Color.GRAY);
        circle.setEffect(LightEffect.getLighting());
        circle.setStroke(Color.BLACK);
        circle.setLayoutX(WINDOW_WIDTH / 2);
        circle.setLayoutY(WINDOW_HEIGHT / 2);

        // Create a rectangle with the specified width, height, and color
        Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.GRAY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setEffect(LightEffect.getLighting());

        // Set the position of the rectangle
        rectangle.setLayoutX(WINDOW_WIDTH / 2 - (0.5 * RECTANGLE_WIDTH));
        rectangle.setLayoutY(WINDOW_WIDTH / 2);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        // Create a rotate transition that rotates the rectangle around the circle for 360 degrees
        RotateTransition rotateTransition = new RotateTransition(ROTATION_DURATION, this);
        rotateTransition.setByAngle(rotationDeg);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);

        rotateTransition.setOnFinished(event -> {
            rotationDeg *= -1;
            rotateTransition.setByAngle(rotationDeg);
        });

        rectangle.setOnMouseClicked(event -> {
            rotateTransition.play();
        });

        getChildren().addAll(rectangle, circle);
        setMaxWidth(WINDOW_WIDTH);
        setMaxHeight(WINDOW_HEIGHT);
    }
}
