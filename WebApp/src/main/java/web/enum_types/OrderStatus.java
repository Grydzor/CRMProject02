package web.enum_types;

public enum OrderStatus {

    OPENED("Opened"), FORMED("Formed"), PAID("Paid"), CLOSED("Closed"),
    UNDER_REVIEW("Under review"), CANCELED("Canceled"), REVIEWED("Reviewed");

    private String label;

    OrderStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}
