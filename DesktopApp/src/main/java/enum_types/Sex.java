package enum_types;

public enum Sex {
    MALE("Male"), FEMALE("Female");

    private String label;

    Sex(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
