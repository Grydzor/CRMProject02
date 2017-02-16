package enum_types;

/**
 * Created by Никита on 15.02.2017.
 */

public enum OrderStatus {

    OPEN("Open"), PAID("Paid"), CLOSED("Closed");

    private String label;

    OrderStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}
