package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.product.ProductService;

import java.util.Objects;

public final class Checkout {
    private final ProductService productService;

    private OrderLineItem orderLineItem = null;

    Checkout(ProductService productService) {
        this.productService = productService;
    }

    public void addItem(String productName) {
        Objects.requireNonNull(productName, "productName cannot be null");

        var selectedProduct = productService.findByName(productName);

        if (Objects.isNull(orderLineItem)) {
            orderLineItem = new OrderLineItem(selectedProduct);
        } else {
            orderLineItem.incrementQuantity();
        }
    }

    public int getTotalCost() {
        return orderLineItem.getLineTotal();
    }
}
