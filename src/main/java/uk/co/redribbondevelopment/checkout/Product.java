package uk.co.redribbondevelopment.checkout;

public class Product {
    private final String name;
    private final int cost;

    public Product(String name, int cost) {
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
