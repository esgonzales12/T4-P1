package dao.domain;

public enum Authority {
    ROOT("ROOT"),
    USER("USER");
    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
