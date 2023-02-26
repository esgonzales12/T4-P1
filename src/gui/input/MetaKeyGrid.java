package gui.input;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class MetaKeyGrid extends GridPane {
    protected final Button enterKey;
    protected final Button deleteKey;
    protected final Button userManagementKey;
    protected final Button changePasswordKey;
    private FontIcon enterIcon;
    private FontIcon deleteIcon;
    private FontIcon userMgmtIcon;
    private FontIcon changePasswordIcon;

    public MetaKeyGrid() {
        enterKey = new Button();
        deleteKey = new Button();
        userManagementKey = new Button();
        changePasswordKey = new Button();
        init();
    }

    private void init() {
        this.initIcons();
        this.initButtons();
        this.setAlignment(Pos.CENTER);
        this.add(enterKey, 0, 0);
        this.add(deleteKey, 1, 0);
        this.add(userManagementKey, 0, 1);
        this.add(changePasswordKey, 1, 1);
    }

    private void initIcons() {
        enterIcon = new FontIcon(FontAwesome.ARROW_CIRCLE_RIGHT);
        deleteIcon = new FontIcon(FontAwesome.UNDO);
        userMgmtIcon = new FontIcon(FontAwesome.USERS);
        changePasswordIcon = new FontIcon(FontAwesome.USER_CIRCLE);

        enterIcon.setFill(Color.GREEN);
        deleteIcon.setFill(Color.RED);
        userMgmtIcon.setFill(Color.BLACK);
        changePasswordIcon.setFill(Color.BLACK);

        enterIcon.setIconSize(30);
        deleteIcon.setIconSize(30);
        userMgmtIcon.setIconSize(30);
        changePasswordIcon.setIconSize(30);
    }

    private void initButtons() {
        enterKey.setGraphic(enterIcon);
        deleteKey.setGraphic(deleteIcon);
        userManagementKey.setGraphic(userMgmtIcon);
        changePasswordKey.setGraphic(changePasswordIcon);

        enterKey.setMinWidth(100);
        deleteKey.setMinWidth(100);
        userManagementKey.setMinWidth(100);
        changePasswordKey.setMinWidth(100);
    }


}
