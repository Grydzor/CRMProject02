package enum_types;

/**
 * Created by eriol4ik on 08.02.17.
 */
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
