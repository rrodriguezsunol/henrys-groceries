package uk.co.redribbondevelopment.checkout.basket;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.Objects;

final class BasketLineItem {
    private final StockItem selectedStockItem;
    private int quantity;

    BasketLineItem(StockItem selectedStockItem) {
        this(selectedStockItem, 1);
    }

    BasketLineItem(StockItem selectedStockItem, int quantity) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        this.selectedStockItem = selectedStockItem;
        setQuantity(quantity);
    }

    int getLineTotal() {
        return selectedStockItem.cost() * quantity;
    }

    int getQuantity() {
        return quantity;
    }

    void incrementQuantity() {
        quantity++;
    }

    String getItemName() {
        return selectedStockItem.name();
    }

    void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity cannot be less than 1. quantity = " + quantity);
        } else if (quantity > 999) {
            throw new IllegalArgumentException("quantity cannot be greater than 999. quantity = " + quantity);
        }

        this.quantity = quantity;
    }
}
