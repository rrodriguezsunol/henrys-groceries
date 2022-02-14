package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.product.Product;
import uk.co.redribbondevelopment.checkout.product.ProductService;

import java.util.Objects;

public final class Checkout {
    private final ProductService productService;

    private Product selectedProduct = null;

    Checkout(ProductService productService) {
        this.productService = productService;
    }

    public void addItem(String productName) {
        Objects.requireNonNull(productName, "productName cannot be null");

        selectedProduct = productService.findByName(productName);
    }

    public int getTotalCost() {
        return selectedProduct.getCost();
    }
}
