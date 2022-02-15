package uk.co.redribbondevelopment.checkout.stock_item;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String itemName) {
        super(String.format("Item '%s' not found", itemName));
    }
}
