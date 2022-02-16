package uk.co.redribbondevelopment.checkout.basket;

import uk.co.redribbondevelopment.checkout.common.StockItemQuantity;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Basket {
    private final List<BasketLineItem> lineItems = new ArrayList<>();

    public void addItem(StockItem selectedStockItem) {
        addItem(selectedStockItem, 1);
    }

    public void addItem(StockItem selectedStockItem, int quantity) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        Optional<BasketLineItem> foundItem = findLineItem(selectedStockItem);

        if (foundItem.isEmpty()) {
            lineItems.add(new BasketLineItem(selectedStockItem, quantity));
        } else {
            BasketLineItem basketLineItem = foundItem.get();
            basketLineItem.setQuantity(basketLineItem.getQuantity() + quantity);
        }
    }

    public int getTotalCost() {
        return lineItems.stream().mapToInt(BasketLineItem::getLineTotal).sum();
    }

    public int getQuantityOf(StockItem stockItem) {
        return findLineItem(stockItem)
                .map(BasketLineItem::getQuantity).orElse(0);
    }

    public Collection<StockItemQuantity> getItemsWithQuantities() {
        return lineItems.stream()
                .map(basketLineItem -> new StockItemQuantity(basketLineItem.getItem(), basketLineItem.getQuantity()))
                .collect(Collectors.toList());
    }

    private Optional<BasketLineItem> findLineItem(StockItem selectedStockItem) {
        return lineItems.stream()
                .filter(basketLineItem -> basketLineItem.getItemName().equals(selectedStockItem.name()))
                .findFirst();
    }
}
