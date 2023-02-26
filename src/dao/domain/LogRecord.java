package dao.domain;

public class LogRecord {
    private final String username;
    private final String operation;
    private final String dateTime;

    public LogRecord(Builder builder) {
        this.username = builder.username;
        this.operation = builder.operation;
        this.dateTime = builder.dateTime;
    }

    @Override
    public String toString() {
        return  username + ',' +
                operation + ',' +
                dateTime + ',' +
                '\n';
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUsername() {
        return username;
    }

    public String getOperation() {
        return operation;
    }

    public static class Builder {
        private String username;
        private String operation;
        private String dateTime;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public LogRecord build() {
            return new LogRecord(this);
        }
    }
}
