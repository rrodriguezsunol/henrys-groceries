package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.util.Objects;

public final class Checkout {
    private final StockItemService stockItemService;

    private Order order;

    Checkout(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    public void addItem(String itemName) {
        Objects.requireNonNull(itemName, "itemName cannot be null");

        var selectedStockItem = stockItemService.findByName(itemName);

        if (Objects.isNull(order)) {
            order = new Order();
        }

        order.addItem(selectedStockItem);
    }

    public int getTotalCost() {
        return order.getTotalCost();
    }
}
