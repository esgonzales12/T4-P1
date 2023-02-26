package gui.util;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class AccessModuleUtil {
    public static final FontIcon ENTER_ICON = new FontIcon(FontAwesome.ARROW_CIRCLE_RIGHT);
    public static final FontIcon DELETE_ICON = new FontIcon(FontAwesome.UNDO);
    public static final FontIcon USER_MGMT_ICON = new FontIcon(FontAwesome.USERS);
    public static final FontIcon CHANGE_PASSWORD_ICON = new FontIcon(FontAwesome.USER_CIRCLE);
    public static final int ALPHA_KEY_GAP = 10;
    public static final int ALPHA_KEY_WIDTH = 60;
    public static final int META_KEY_WIDTH = 100;
    public static final int META_ICON_SIZE = 30;
    public static final int ALPHA_GRID_COLS = 3;
    public static final int SAM_WIDTH = 220;
    public static final int SAM_SPACING = 20;

    public static final String SAM_STYLE = """
                    -fx-background-color: #747474;
                    -fx-background-radius: 20px;
                    -fx-padding: 10;
                    """;
}
