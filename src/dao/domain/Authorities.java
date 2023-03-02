package dao.domain;

public enum Authorities {
    ADMIN,
    USER,
    GUEST, 
    SAVE, 
    DELETE, 
    GET, 
    MODIFY;

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
