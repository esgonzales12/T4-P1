package gui.input;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class UsbDrive extends Pane {

    private static final double TOTAL_WIDTH = 150;
    private static final double HEIGHT = 50;
    private static final double DRIVE_WIDTH = TOTAL_WIDTH - 30;


    public UsbDrive() {
        FontIcon usbIcon = new FontIcon(FontAwesome.USB);
        usbIcon.setFill(Color.WHITE);
        usbIcon.setIconSize(35);
        Button outer = new Button();
        outer.setStyle("""
                            -fx-background-radius: 5;
                            -fx-background-color: black;
                            """);
        outer.setBackground(Background.fill(Color.BLACK));
        outer.setGraphic(usbIcon);
        outer.setMinWidth(DRIVE_WIDTH);
        outer.setMinHeight(HEIGHT);
        outer.setLayoutX(40);
        outer.setLayoutY(0);

        Rectangle inner = new Rectangle(TOTAL_WIDTH, HEIGHT - 10, Color.GRAY);
        inner.setStroke(Color.valueOf("2F2F2FFF"));
        inner.setLayoutX(0);
        inner.setLayoutY(5);

        setMaxWidth(TOTAL_WIDTH);
        setMaxHeight(HEIGHT);

        getChildren().addAll(inner, outer);
    }

    public double getUsbWidth() {
        return TOTAL_WIDTH;
    }

    public double getConnectorWidth() {
        return TOTAL_WIDTH - DRIVE_WIDTH;
    }
}
