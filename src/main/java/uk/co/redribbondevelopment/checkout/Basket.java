package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Order {
    private final List<OrderLineItem> lineItems = new ArrayList<>();

    public void addItem(StockItem selectedStockItem) {
        Objects.requireNonNull(selectedStockItem, "selectedStockItem cannot be null");

        Optional<OrderLineItem> foundItem = lineItems.stream()
                .filter(orderLineItem -> orderLineItem.getItemName().equals(selectedStockItem.getName()))
                .findFirst();

        if (foundItem.isEmpty()) {
            lineItems.add(new OrderLineItem(selectedStockItem));
        } else {
            foundItem.get().incrementQuantity();
        }
    }

    public int getTotalCost() {
        return lineItems.stream().mapToInt(OrderLineItem::getLineTotal).sum();
    }
}
