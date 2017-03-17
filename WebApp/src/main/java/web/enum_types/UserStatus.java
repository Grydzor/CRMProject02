package web.enum_types;

public enum UserStatus {
    UNKNOWN_USER("Unknown user"), WRONG_PASSWORD("Wrong password"), SUCCESS("");

    private String label;

    UserStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
