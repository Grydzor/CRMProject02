package web.enum_types;

public enum IPhoneColor {
    JET_BLACK("Jet Black"), BLACK("Black"), SPACE_GRAY("Space Gray"), SILVER("Silver"), GOLD("Gold"), ROSE_GOLD("Rose Gold"), RED("Red");

    private String label;

    IPhoneColor(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
