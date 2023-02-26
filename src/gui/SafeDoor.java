package gui;

import gui.displays.LightContainer;
import gui.hardware.LockingBolts;
import gui.hardware.SafeHandle;
import gui.input.Keypad;
import gui.input.MetaKeyGrid;
import gui.input.UsbDrive;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SafeDoor extends StackPane {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;
    private Rectangle door;
    private SafeHandle handle;
    private LightContainer lightContainer;
    private Keypad keypad;
    private LockingBolts bolts;
    double toggle = -1;
    public SafeDoor(double windowWidth, double windowHeight) {
        door = new Rectangle(WIDTH, HEIGHT, Color.valueOf("#3f3e3e"));
        door.setEffect(LightEffect.getLighting());
        bolts = new LockingBolts(WIDTH + 150, HEIGHT);
        handle = new SafeHandle();
        keypad = new Keypad();
        keypad.setKeyClickHandler("A", event -> bolts.disengage());
        keypad.setKeyClickHandler("B", event -> bolts.engage());
        lightContainer = new LightContainer();
        door.setArcHeight(20);
        door.setArcWidth(20);

        VBox accessModule = new VBox();

        accessModule.setAlignment(Pos.CENTER);
        accessModule.setSpacing(20);
        accessModule.getChildren().addAll(lightContainer, keypad, new MetaKeyGrid());
        accessModule.setMaxWidth(220);
        accessModule.setStyle("""
                                -fx-background-color: #747474;
                                -fx-background-radius: 20px;
                                -fx-padding: 10;
                                """);

        keypad.setKeyClickHandler("F", event -> {
            System.out.printf("""
                    (%f,%f)
                    %n""", accessModule.getLayoutX(), accessModule.getLayoutY());
        });
        VBox keypadAndHandle = new VBox();
        keypadAndHandle.setEffect(LightEffect.getLighting());
        keypadAndHandle.setAlignment(Pos.TOP_CENTER);
        keypadAndHandle.getChildren().addAll(accessModule, handle);
        keypadAndHandle.setSpacing(10);
        keypadAndHandle.setMaxHeight(700);

        UsbDrive usb = new UsbDrive();
        usb.setLayoutX(windowWidth - (usb.getUsbWidth() + 100));
        usb.setLayoutY(260);

        Pane pane = new Pane();
        pane.setBackground(Background.fill(Color.rgb(255,0,0,0.2)));
        pane.getChildren().add(usb);

        double targetX = 660;
        double targetY = 260;
        double translateX = usb.getLayoutX() - targetX;
        double translateY = usb.getLayoutY() - targetY;


        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.75), pane);
        transition.setByX(-translateX);
        transition.setByY(-translateY);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> {
            toggle *= -1;
            transition.setByX(toggle * translateX);
            transition.setByY(toggle * translateY);
        });

        keypad.setKeyClickHandler("E", event -> transition.play());

        getChildren().addAll(bolts, door, pane,  keypadAndHandle);

        setAlignment(Pos.CENTER);
    }
}
