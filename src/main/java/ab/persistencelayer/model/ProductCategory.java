package ab.persistencelayer.model;


public enum ProductCategory {
    PC(0), LAPTOP(1), PHONE(2), ACCESSORIES(3), OTHERS(4);

    private final int value;

    ProductCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
