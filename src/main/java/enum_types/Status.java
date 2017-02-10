package enum_types;

/**
 * Created by eriol4ik on 08.02.17.
 */
public enum Status {
    UNKNOWN_USER("Unknown user"), WRONG_PASSWORD("Wrong password"), SUCCESS("");

    private String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
