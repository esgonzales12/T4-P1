package gui.displays.impl;

import gui.displays.StateDisplay;
import gui.enums.StateDisplayType;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StateDisplayImpl extends Rectangle implements StateDisplay {
    private static final Color LIGHT_OFF_COLOR = Color.valueOf("808080");
    private static final double LIGHT_ON_LEVEL = 1;
    private static final double LIGHT_OFF_LEVEL = 0.2;
    private static final int ARC_SIZE = 10;
    private final FadeTransition flashAnimation;
    private final FadeTransition blinkAnimation;
    private final FadeTransition solidAnimation;
    private final Glow glow;

    public StateDisplayImpl(double width, double height) {
        super(width, height, LIGHT_OFF_COLOR);
        glow = new Glow(LIGHT_OFF_LEVEL);
        flashAnimation = new FadeTransition(Duration.seconds(0.25), this);
        blinkAnimation = new FadeTransition(Duration.seconds(0.5), this);
        solidAnimation = new FadeTransition(Duration.seconds(0.1), this);
        initAnimations();
        setEffect(glow);
        setArcWidth(ARC_SIZE);
        setArcHeight(ARC_SIZE);
    }

    @Override
    public void display(Color color, StateDisplayType type) {
        flashAnimation.stop();
        blinkAnimation.stop();
        solidAnimation.stop();
        glow.setLevel(LIGHT_ON_LEVEL);
        setFill(color);
        switch (type) {
            case FLASH -> flashAnimation.play();
            case BLINKING -> blinkAnimation.play();
            case SOLID -> solidAnimation.play();
        }
    }

    @Override
    public void off() {
        flashAnimation.stop();
        blinkAnimation.stop();
        solidAnimation.stop();
        setOpacity(1.0);
        setFill(LIGHT_OFF_COLOR);
        glow.setLevel(LIGHT_OFF_LEVEL);
    }

    private void initAnimations() {
        solidAnimation.setFromValue(1.0);
        solidAnimation.setToValue(0.8);
        solidAnimation.setCycleCount(Animation.INDEFINITE);
        solidAnimation.setAutoReverse(true);
        solidAnimation.setOnFinished(e -> this.setOpacity(1.0));

        flashAnimation.setFromValue(1.0);
        flashAnimation.setToValue(0.95);
        flashAnimation.setCycleCount(2);
        flashAnimation.setAutoReverse(true);
        flashAnimation.setOnFinished(e -> {
            this.setOpacity(1.0);
            this.setFill(LIGHT_OFF_COLOR);
            glow.setLevel(LIGHT_OFF_LEVEL);
        });

        blinkAnimation.setFromValue(1.0);
        blinkAnimation.setToValue(0.5);
        blinkAnimation.setCycleCount(Animation.INDEFINITE);
        blinkAnimation.setAutoReverse(true);
        blinkAnimation.setOnFinished(e -> this.setOpacity(1.0));
    }
}
