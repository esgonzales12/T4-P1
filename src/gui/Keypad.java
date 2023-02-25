package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Keypad extends GridPane implements Iterable<Button>{
    private static final String DIGITS = "123456789ABCDEF";
    private static final int ROWS = 5;
    private static final int COLS = 3;
    private static final int KEY_WIDTH = 60;
    private final Map<String, Button> keys;

    public Keypad() {
        keys = new HashMap<>();
        issueButtons();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
    }

    protected void setKeyClickHandler(String keyName, EventHandler<MouseEvent> handler) {
        if (!keys.containsKey(keyName)) {
            System.out.println("NO KEY FOUND");
            return;
        }
        keys.get(keyName).setOnMouseClicked(handler);
    }

    private void issueButtons() {
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                String digit = String.valueOf(DIGITS.charAt(index++));
                Button key = new Button(digit);
                key.setMinWidth(KEY_WIDTH);
                keys.put(digit, key);
                add(key, j, i);
            }
        }
    }

    @Override
    public Iterator<Button> iterator() {
        return keys.values().iterator();
    }
}
