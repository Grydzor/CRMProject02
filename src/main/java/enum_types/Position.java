package enum_types;

public enum Position {
    ADMIN("Admin"), MANAGER("Manager"), CASHIER("Cashier");

    private String label;

    Position(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
