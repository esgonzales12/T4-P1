package dao.domain;

public enum Operation {
    USER_MANAGEMENT,
    CHANGE_PASSWORD,
    EXPORT_LOGS,
    BASE_ACCESS,
    CANCEL;

    public boolean contains(Operation operation) {
        for (Operation op : Operation.values()) {
            if (op.equals(operation)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        // Reset the enum value to its default state
        switch (this) {
            case USER_MANAGEMENT:
                // Reset user management permissions
                break;
            case CHANGE_PASSWORD:
                // Reset change password permissions
                break;
            case EXPORT_LOGS:
                // Reset export logs permissions
                break;
            case BASE_ACCESS:
                // Reset base access permissions
                break;
            default:
                // Do nothing
                break;
        }
    }
}
