package gui.impl.container;

import gui.LedDisplay;
import gui.StateDisplay;
import gui.impl.LedDisplayImpl;
import gui.impl.StateDisplayImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class LightContainer extends VBox {

    public LedDisplay ledDisplay;
    public StateDisplay stateDisplay;

    public LightContainer() {
        ledDisplay = new LedDisplayImpl(200, 50);
        stateDisplay = new StateDisplayImpl(200, 10);
        this.getChildren().addAll((Rectangle) stateDisplay, (Node) ledDisplay);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }


}
