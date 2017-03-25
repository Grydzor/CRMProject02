package web.enum_types;

public enum Color {
    JET_BLACK("Jet Black"), BLACK("Black"), SPACE_GRAY("Space Gray"), SILVER("Silver"), GOLD("Gold"), ROSE_GOLD("Rose Gold");

    private String label;

    Color(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
