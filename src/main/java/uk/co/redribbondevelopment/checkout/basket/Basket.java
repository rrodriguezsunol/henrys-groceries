package uk.co.redribbondevelopment.checkout.basket;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Basket {
    private final List<BasketLineItem> lineItems = new ArrayList<>();

    public void addItem(StockItem selectedStockItem) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        Optional<BasketLineItem> foundItem = lineItems.stream()
                .filter(basketLineItem -> basketLineItem.getItemName().equals(selectedStockItem.name()))
                .findFirst();

        if (foundItem.isEmpty()) {
            lineItems.add(new BasketLineItem(selectedStockItem));
        } else {
            foundItem.get().incrementQuantity();
        }
    }

    public int getTotalCost() {
        return lineItems.stream().mapToInt(BasketLineItem::getLineTotal).sum();
    }
}
