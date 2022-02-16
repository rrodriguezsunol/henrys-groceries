package uk.co.redribbondevelopment.checkout.common;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

public record StockItemQuantity(StockItem stockItem, int quantity) {
}
