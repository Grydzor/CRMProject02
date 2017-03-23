package web.enum_types;

public enum IPhoneCapacity {
    GB_32("32GB"), GB_128("128GB"), GB_256("256GB");
    private String label;

    IPhoneCapacity(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
