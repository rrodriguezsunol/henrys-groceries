package uk.co.redribbondevelopment.checkout.stock_item;

import java.util.List;

public class InMemoryStockItemService implements StockItemService {

    private final List<StockItem> stockItemDefinitions = List.of(
            new StockItem("soup", 65),
            new StockItem("bread", 80),
            new StockItem("milk", 130),
            new StockItem("apples", 10)
    );

    @Override
    public StockItem findByName(String name) {
        return stockItemDefinitions.stream()
                .filter(stockItem -> stockItem.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(name));
    }
}
