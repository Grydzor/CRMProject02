package web.enum_types;


public enum PaymentType {
    CASH("Cash"), CASH_ON_DELIVERY("Cash On Delivery"), ELECTRONIC_PAYMENT("Electronic payment");

    private String label;

    PaymentType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
