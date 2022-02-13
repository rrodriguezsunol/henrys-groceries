package uk.co.redribbondevelopment.checkout;

public final class Checkout {

    private Checkout() {

    }

    public static Checkout startNew() {
        return new Checkout();
    }

    public void addItem(String productName) {

    }

    public int getTotalCost() {
        return 0;
    }
}
