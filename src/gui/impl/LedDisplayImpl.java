package gui.impl;

import gui.LedDisplay;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static java.util.Objects.isNull;

public class LedDisplayImpl extends StackPane implements LedDisplay {
    private static final Color DISPLAY_ON_COLOR =  Color.valueOf("53A8EEFF");
    private static final Color DISPLAY_OFF_COLOR = Color.valueOf("808080");
    private static final double DISPLAY_ON_LEVEL = 0.7;
    private static final double DISPLAY_OFF_LEVEL = 0.2;
    private final FadeTransition fadeAnimation;
    private final Rectangle ledDisplay;
    private final Label displayText;
    private final Glow glow;

    public LedDisplayImpl(double width, double height) {
        ledDisplay = new Rectangle(width, height, DISPLAY_OFF_COLOR);
        displayText = new Label();
        glow = new Glow(DISPLAY_OFF_LEVEL);
        fadeAnimation = new FadeTransition(Duration.seconds(0.5), this);
        displayText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        ledDisplay.setEffect(glow);
        fadeAnimation.setFromValue(1.0);
        fadeAnimation.setToValue(0.95);
        fadeAnimation.setCycleCount(Animation.INDEFINITE);
        fadeAnimation.setAutoReverse(true);
        fadeAnimation.setOnFinished(e -> this.setOpacity(1.0));
        getChildren().addAll(ledDisplay, displayText);
    }

    @Override
    public void backlightOn() {
        Platform.runLater(() -> {
            glow.setLevel(DISPLAY_ON_LEVEL);
            ledDisplay.setFill(DISPLAY_ON_COLOR);
            fadeAnimation.play();
        });
    }

    @Override
    public void backlightOff() {
        Platform.runLater(() -> {
            glow.setLevel(DISPLAY_OFF_LEVEL);
            ledDisplay.setFill(DISPLAY_OFF_COLOR);
            fadeAnimation.stop();
        });
    }

    @Override
    public void setDisplayText(String prompt, String input) {
        Platform.runLater(() -> {
            if (isNull(prompt) || prompt.isEmpty()) {
                displayText.setText(input);
                return;
            }
            displayText.setText(String.format(
                    """
                            %s
                            %s
                            """, prompt, input));
        });
    }

    @Override
    public void clearDisplayText() {
        Platform.runLater(() -> displayText.setText(""));
    }

}
