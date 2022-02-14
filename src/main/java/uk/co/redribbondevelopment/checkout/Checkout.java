package uk.co.redribbondevelopment.checkout;

import java.util.List;
import java.util.Objects;

public final class Checkout {
    private Product selectedProduct = null;
    private final List<Product> productDefinitions = List.of(
            new Product("soup", 65),
            new Product("bread", 80),
            new Product("milk", 130),
            new Product("apples", 10)
    );

    private Checkout() {

    }

    public static Checkout startNew() {
        return new Checkout();
    }

    public void addItem(String productName) {
        Objects.requireNonNull(productName, "productName cannot be null");

        selectedProduct = productDefinitions.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productName));
    }

    public int getTotalCost() {
        return selectedProduct.getCost();
    }
}
