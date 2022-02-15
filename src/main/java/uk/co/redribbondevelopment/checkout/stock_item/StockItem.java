package uk.co.redribbondevelopment.checkout.stock_item;

public final class StockItem {
    private final String name;
    private final int cost;

    public StockItem(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
