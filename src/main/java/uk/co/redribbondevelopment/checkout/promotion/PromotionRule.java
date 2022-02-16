package uk.co.redribbondevelopment.checkout.promotion;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;

final class PromotionRule {
    private final String name;
    private final LocalDate validFrom;
    private final LocalDate validTo;

    private final StockItem applicableStockItem;
    private final int discountPercentage;

    PromotionRule(String name, LocalDate validFrom, LocalDate validTo, StockItem applicableStockItem, int discountPercentage) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.applicableStockItem = applicableStockItem;
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public int calculateDiscountAmount(int quantity) {
        var totalPriceBeforeDiscount = quantity * getApplicableStockItem().cost();

        return  (totalPriceBeforeDiscount * discountPercentage) / 100;
    }

    public StockItem getApplicableStockItem() {
        return applicableStockItem;
    }
}
