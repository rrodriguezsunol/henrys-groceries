package uk.co.redribbondevelopment.checkout.promotions;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;

final class PromotionRule {
    private final String name;
    final LocalDate validFrom;
    final LocalDate validTo;

    final StockItem applicableStockItem;
    final int discountPercentage;

    PromotionRule(String name, LocalDate validFrom, LocalDate validTo, StockItem applicableStockItem, int discountPercentage) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.applicableStockItem = applicableStockItem;
        this.discountPercentage = discountPercentage;
    }


}
