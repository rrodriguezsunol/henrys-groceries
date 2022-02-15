package uk.co.redribbondevelopment.checkout.basket;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.Objects;

final class BasketLineItem {
    private final StockItem selectedStockItem;
    private int quantity;

    BasketLineItem(StockItem selectedStockItem) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        this.selectedStockItem = selectedStockItem;
        this.quantity = 1;
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
}
