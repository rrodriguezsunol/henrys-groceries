package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.product.Product;

import java.util.Objects;

public class OrderLineItem {
    private final Product selectedProduct;
    private int quantity;

    public OrderLineItem(Product selectedProduct) {
        Objects.requireNonNull(selectedProduct, "selectedProduct cannot be null");

        this.selectedProduct = selectedProduct;
        this.quantity = 1;
    }

    public int getLineTotal() {
        return selectedProduct.getCost() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }
}
