package gui.impl.container;

import gui.util.LightEffect;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static gui.util.SafeUtil.*;

public class LockingMechanism extends Pane {

    private final TranslateTransition transition;
    private boolean locked = true;
    private int toggle = 1;

    public LockingMechanism(double width, double height) {
        Rectangle[] bolts = new Rectangle[6];
        double yIncr = (height - 150) / bolts.length;
        double y = yIncr;
        for (int i = 0; i < bolts.length; i++) {
            bolts[i] = new Rectangle(BOLT_WIDTH, BOLT_HEIGHT, Color.SILVER);
            bolts[i].setLayoutX(0);
            bolts[i].setLayoutY(y);
            bolts[i].setArcWidth(20);
            bolts[i].setArcHeight(20);
            bolts[i].setStroke(Color.GRAY);
            bolts[i].setEffect(LightEffect.getLighting());
            y += yIncr;
            getChildren().add(bolts[i]);
        }
        transition = new TranslateTransition(Duration.seconds(0.5), this);
        transition.setByX(BOLT_WIDTH);
        transition.setByY(0);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> {
            locked = !locked;
            toggle *= -1;
            transition.setByX(BOLT_WIDTH * toggle);
        });
        setMaxWidth(width);
        setMaxHeight(height);
    }

    public void engage() {
        if (locked) return;
        transition.play();
    }

    public void disengage() {
        if (!locked) return;
        transition.play();
    }
}
