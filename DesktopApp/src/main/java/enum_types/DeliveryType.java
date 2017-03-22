package enum_types;

public enum DeliveryType {
    PICKUP("Pickup"), NOVA_POSHTA("Nova Poshta"), COURIER("courier");

    private String label;

    DeliveryType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
