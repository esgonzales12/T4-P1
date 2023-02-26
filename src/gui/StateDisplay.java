package gui;

import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;

public interface StateDisplay {
    void display(Color color, StateDisplayType type);
    void off();
}
