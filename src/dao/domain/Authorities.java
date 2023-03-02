package dao.domain;

public enum Authorities {
   
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
    PASSWORD_MANAGEMENT("*"),
    CANCEL("#");

    private final String value;

    Authorities(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


    public boolean contains(Authorities authority) {
        for (Authorities a : values()) {
            if (a == authority) {
                return true;
            }
        }
        return false;
    }
    
    public static void clear() {
        // clear all authorities here
    }
}
