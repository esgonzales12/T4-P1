package gui.impl.container;

import drivers.KeypadController;
import drivers.enums.Key;
import gui.LedDisplay;
import gui.StateDisplay;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

import static gui.util.AccessModuleUtil.*;


public class AccessModule extends VBox {
    private final LightContainer lightContainer;
    private final GridPane alphaKeys;
    private final GridPane metaKeys;
    private final Map<Key, Button> keys;
    private KeypadController controller;

    public AccessModule() {
        keys = new HashMap<>();
        alphaKeys = new GridPane();
        metaKeys = new GridPane();
        lightContainer = new LightContainer();
        setStyle();
        issueKeys();
    }

    public LedDisplay getLedDisplay() {
        return lightContainer.ledDisplay;
    }

    public StateDisplay getStateDisplay() {
        return lightContainer.stateDisplay;
    }

    public void setKeypadController(KeypadController controller) {
        this.controller = controller;
    }

    public void setButtonClickHandler(Key key, EventHandler<MouseEvent> handler) {
        if (!keys.containsKey(key)) {
            System.out.println("KEY NOT FOUND: " + key);
            return;
        }
        keys.get(key).setOnMouseClicked(handler);
    }

    private void setStyle() {
        alphaKeys.setAlignment(Pos.CENTER);
        alphaKeys.setHgap(ALPHA_KEY_GAP);
        alphaKeys.setVgap(ALPHA_KEY_GAP);
        metaKeys.setAlignment(Pos.CENTER);
        setSpacing(SAM_SPACING);
        setMaxWidth(SAM_WIDTH);
        setStyle(SAM_STYLE);
        setAlignment(Pos.CENTER);
        getChildren().addAll(lightContainer, alphaKeys, metaKeys);
    }

    private void issueKeys() {
        styleIcons();
        int ind = 0;
        for (Key k: Key.values()) {
            Button key = new Button();
            if (!k.getValue().equals("META")) {
                key.setMinWidth(ALPHA_KEY_WIDTH);
                alphaKeys.add(key,
                        ind  % ALPHA_GRID_COLS,
                        ind / ALPHA_GRID_COLS);
                key.setText(k.getValue());
                ind++;
            } else {
                switch (k) {
                    case ENTER -> {
                        key.setGraphic(ENTER_ICON);
                        metaKeys.add(key, 0,0);
                    }
                    case BACKSPACE -> {
                        key.setGraphic(DELETE_ICON);
                        metaKeys.add(key, 1, 0);
                    }
                    case USER_MANAGEMENT -> {
                        key.setGraphic(USER_MGMT_ICON);
                        metaKeys.add(key, 0, 1);
                    }
                    case CHANGE_PASSWORD -> {
                        key.setGraphic(CHANGE_PASSWORD_ICON);
                        metaKeys.add(key, 1, 1);
                    }
                }
                key.setMinWidth(META_KEY_WIDTH);
            }
            key.setOnMouseClicked(pressHandler(k));
            keys.put(k, key);
        }
    }

    private static void styleIcons() {
        ENTER_ICON.setIconSize(META_ICON_SIZE);
        DELETE_ICON.setIconSize(META_ICON_SIZE);
        USER_MGMT_ICON.setIconSize(META_ICON_SIZE);
        CHANGE_PASSWORD_ICON.setIconSize(META_ICON_SIZE);

        ENTER_ICON.setFill(Color.GREEN);
        DELETE_ICON.setFill(Color.RED);
        USER_MGMT_ICON.setFill(Color.BLACK);
        CHANGE_PASSWORD_ICON.setFill(Color.BLACK);
    }

    private EventHandler<MouseEvent> pressHandler(Key k) {
        return event -> {
            if (controller != null) {
                controller.receiveKey(k);
            }
        };
    }

}
