package drivers.enums;

public enum Key {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    ENTER("META"),
    BACKSPACE("META"),
    USER_MANAGEMENT("META"),
    CHANGE_PASSWORD("META");


    private final String value;

    Key(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
