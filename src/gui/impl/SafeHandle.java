package gui.impl;

import gui.LockActuator;
import gui.impl.container.LockingMechanism;
import gui.util.LightEffect;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.atomic.AtomicBoolean;

import static gui.util.SafeUtil.*;


public class SafeHandle extends Pane {
    private final RotateTransition turnTransition;
    private final RotateTransition lockedTransition;
    private final AtomicBoolean turning;
    private final Rectangle handleLower;
    private final Circle handleUpper;
    private LockingMechanism lockingMechanism;
    private LockActuator lockActuator;
    private boolean mechanismEngaged;
    private int turnDegrees;

    public SafeHandle() {
        turnDegrees = -60;
        mechanismEngaged = true;
        turning = new AtomicBoolean(false);
        handleUpper = new Circle(HANDLE_RADIUS, Color.GRAY);
        handleLower = new Rectangle(HANDLE_WIDTH, HANDLE_HEIGHT, Color.GRAY);
        lockedTransition = new RotateTransition(HANDLE_LOCKED_DURATION, this);
        turnTransition = new RotateTransition(HANDLE_TURN_DURATION, this);
        init();
    }

    public void setLockActuator(LockActuator actuator) {
        this.lockActuator = actuator;
    }

    public void setLockingMechanism(LockingMechanism mechanism) {
        lockingMechanism = mechanism;
    }

    private void init() {
        handleUpper.setEffect(LightEffect.getLighting());
        handleUpper.setStroke(Color.BLACK);
        handleUpper.setLayoutX(HANDLE_FRAME_SIZE / 2);
        handleUpper.setLayoutY(HANDLE_FRAME_SIZE / 2);

        handleLower.setStroke(Color.BLACK);
        handleLower.setEffect(LightEffect.getLighting());
        handleLower.setLayoutX(HANDLE_FRAME_SIZE / 2 - (0.5 * HANDLE_WIDTH));
        handleLower.setLayoutY(HANDLE_FRAME_SIZE / 2);
        handleLower.setArcWidth(10);
        handleLower.setArcHeight(10);

        lockedTransition.setByAngle(HANDLE_LOCKED_DEGREES);
        lockedTransition.setCycleCount(2);
        lockedTransition.setAutoReverse(true);
        lockedTransition.setInterpolator(Interpolator.LINEAR);
        lockedTransition.setOnFinished(event -> turning.set(false));

        turnTransition.setByAngle(turnDegrees);
        turnTransition.setCycleCount(1);
        turnTransition.setAutoReverse(false);
        turnTransition.setInterpolator(Interpolator.LINEAR);

        turnTransition.setOnFinished(event -> {
            turnDegrees *= -1;
            turnTransition.setByAngle(turnDegrees);
            turning.set(false);
        });

        handleLower.setOnMouseClicked(openHandler());
        getChildren().addAll(handleLower, handleUpper);
        setMaxWidth(HANDLE_FRAME_SIZE);
        setMaxHeight(HANDLE_FRAME_SIZE);
    }

    private EventHandler<MouseEvent> openHandler() {
        return event -> {
            if (turning.get()) return;

            if (lockActuator == null || lockActuator.engaged()) {
                lockedTransition.play();
            } else if (mechanismEngaged) {
                turnHandle();
                if (lockingMechanism != null) lockingMechanism.disengage();
                mechanismEngaged = false;
            } else {
                turnHandle();
                if (lockingMechanism != null) lockingMechanism.engage();
                ((LockActuatorImpl)lockActuator).engage();
                mechanismEngaged = true;
            }
        };
    }

    private void turnHandle() {
        turning.set(true);
        turnTransition.play();
    }
}
