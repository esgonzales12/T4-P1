package gui.impl.container;

import drivers.UsbDriver;
import gui.util.SafeUtil;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import static gui.util.SafeUtil.USB_DEMO_DURATION;

public class UsbDemo extends Pane {
    private final TranslateTransition transition;
    private final UsbDrive usb;
    private UsbDriver usbDriver;
    private double translateHorizontal;
    private double translateVertical;
    private double toggle = -1;
    private boolean connected;

    public UsbDemo(double windowWidth, double windowHeight) {
        usb = new UsbDrive();
        transition = new TranslateTransition(USB_DEMO_DURATION, this);
        connected = false;
        init(windowWidth, windowHeight);
    }

    public void setUsbDriver(UsbDriver usbDriver) {
        this.usbDriver = usbDriver;
    }

    private EventHandler<MouseEvent> usbClickHandler() {
        return event -> {
            if (transition != null) {
                transition.play();
            }
        };
    }

    private void init(double w, double h) {
        usb.setLayoutX(w - (usb.getUsbWidth() + 100));
        usb.setLayoutY(SafeUtil.USB_DRIVE_TARGET_Y);
        translateHorizontal = usb.getLayoutX() - SafeUtil.USB_DRIVE_TARGET_X;
        translateVertical = usb.getLayoutY() - SafeUtil.USB_DRIVE_TARGET_Y;
        transition.setByX(-translateHorizontal);
        transition.setByY(-translateVertical);
        transition.setCycleCount(1);
        transition.setOnFinished(connectHandler());
        usb.driveBase.setOnMouseClicked(usbClickHandler());
        getChildren().add(usb);
    }

    private EventHandler<ActionEvent> connectHandler() {
        return event -> {
            if (connected) {
                connected = false;
                if (usbDriver != null) usbDriver.disconnect();
            } else {
                connected = true;
                if (usbDriver!= null) usbDriver.connect();
            }
            toggle *= -1;
            transition.setByX(toggle * translateHorizontal);
            transition.setByY(toggle * translateVertical);
        };
    }

    private static class UsbDrive extends Pane {

        private static final double TOTAL_WIDTH = 150;
        private static final double HEIGHT = 50;
        private static final double DRIVE_WIDTH = TOTAL_WIDTH - 30;
        private final Button driveBase;

        public UsbDrive() {
            FontIcon usbIcon = new FontIcon(FontAwesome.USB);
            usbIcon.setFill(Color.WHITE);
            usbIcon.setIconSize(35);
            driveBase = new Button();
            driveBase.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: black;
                            """);
            driveBase.setBackground(Background.fill(Color.BLACK));
            driveBase.setGraphic(usbIcon);
            driveBase.setMinWidth(DRIVE_WIDTH);
            driveBase.setMinHeight(HEIGHT);
            driveBase.setLayoutX(40);
            driveBase.setLayoutY(0);

            Rectangle driveConnector = new Rectangle(TOTAL_WIDTH, HEIGHT - 10, Color.GRAY);
            driveConnector.setStroke(Color.valueOf("2F2F2FFF"));
            driveConnector.setLayoutX(0);
            driveConnector.setLayoutY(5);

            setMaxWidth(TOTAL_WIDTH);
            setMaxHeight(HEIGHT);

            getChildren().addAll(driveConnector, driveBase);
        }

        public double getUsbWidth() {
            return TOTAL_WIDTH;
        }
    }
}
