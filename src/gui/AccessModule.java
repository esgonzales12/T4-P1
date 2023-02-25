package gui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AccessModule extends Application {

    private final Color DISPLAY_ON_COLOR =  Color.valueOf("53A8EEFF");
    private final Color DISPLAY_OFF_COLOR = Color.valueOf("808080");
    private FadeTransition fadeAnimation;
    private Rectangle ledDisplay;
    private Label label;
    private Glow glow;
    private boolean displayOn = false;

    @Override
    public void start(Stage primaryStage) {
        label = new Label();
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        ledDisplay = new Rectangle(200, 50, DISPLAY_OFF_COLOR);
        glow = new Glow();
        glow.setLevel(0.2);
        Keypad keyboard = new Keypad();
        keyboard.setAlignment(Pos.CENTER);
        keyboard.forEach(button -> {
            button.setOnMouseClicked(event -> label.setText(button.getText()));
            button.setStyle("-fx-effect: dropshadow(gaussian, #696969, 4, 0.5, 0, 0.5);");
        });
        keyboard.setKeyClickHandler("A", event -> {
            displayOn = !displayOn;
            toggleDisplay();
        });

        StackPane ledDisplay = new StackPane(this.ledDisplay, label);
        ledDisplay.setEffect(glow);

        VBox display = new VBox();
        display.setSpacing(20);
        display.getChildren().addAll(ledDisplay, keyboard, new MetaKeyGrid());
        display.setPadding(new Insets(10));

        Scene scene = new Scene(display);
        primaryStage.setTitle("LED Keyboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void toggleDisplay() {
        if (displayOn) {
            animateGlow();
            glow.setLevel(0.7);
            ledDisplay.setFill(DISPLAY_ON_COLOR);
            return;
        }
        fadeAnimation.stop();
        glow.setLevel(0.2);
        ledDisplay.setFill(DISPLAY_OFF_COLOR);
    }

    private void animateGlow() {
        if (fadeAnimation == null) {
            fadeAnimation = new FadeTransition(Duration.seconds(0.5), ledDisplay);
            fadeAnimation.setFromValue(1.0);
            fadeAnimation.setToValue(0.95);
            fadeAnimation.setCycleCount(Animation.INDEFINITE);
            fadeAnimation.setAutoReverse(true);
            fadeAnimation.setOnFinished(e -> ledDisplay.setOpacity(1.0));
            fadeAnimation.play();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
