package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.Objects;

public class OrderLineItem {
    private final StockItem selectedStockItem;
    private int quantity;

    public OrderLineItem(StockItem selectedStockItem) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        this.selectedStockItem = selectedStockItem;
        this.quantity = 1;
    }

    public int getLineTotal() {
        return selectedStockItem.getCost() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }
}
