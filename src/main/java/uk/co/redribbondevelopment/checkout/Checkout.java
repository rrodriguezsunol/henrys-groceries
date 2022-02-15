package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.util.Objects;

public final class Checkout {
    private final StockItemService stockItemService;

    private Basket basket;

    Checkout(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    public void addItem(String itemName) {
        Objects.requireNonNull(itemName, "itemName cannot be null");

        var selectedStockItem = stockItemService.findByName(itemName);

        if (Objects.isNull(basket)) {
            basket = new Basket();
        }

        basket.addItem(selectedStockItem);
    }

    public int getTotalCost() {
        return basket.getTotalCost();
    }
}
