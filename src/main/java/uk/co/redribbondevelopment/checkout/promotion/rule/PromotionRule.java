package uk.co.redribbondevelopment.checkout.promotion.rule;

import uk.co.redribbondevelopment.checkout.common.StockItemQuantity;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

public final class PromotionRule {
    private final String name;
    private final LocalDate validFrom;
    private final LocalDate validTo;

    private final StockItem applicableStockItem;
    private final int discountPercentage;

    public PromotionRule(String name, LocalDate validFrom, LocalDate validTo, StockItem applicableStockItem, int discountPercentage) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.applicableStockItem = applicableStockItem;
        this.discountPercentage = discountPercentage;
    }

    public boolean isActiveOn(LocalDate today) {
        return (today.isEqual(validFrom) || today.isAfter(validFrom))
                && (today.isEqual(validTo) || today.isBefore(validTo));
    }

    public int getDiscountAmount() {
        return  (getApplicableStockItem().cost() * discountPercentage) / 100;
    }

    public StockItem getApplicableStockItem() {
        return applicableStockItem;
    }

    public int getNumberOfMatches(Collection<StockItemQuantity> stockItemQuantities) {
        return stockItemQuantities.stream()
                .filter(stockItemQuantity -> stockItemQuantity.stockItem().equals(applicableStockItem))
                .map(StockItemQuantity::quantity)
                .findFirst()
                .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionRule that = (PromotionRule) o;
        return discountPercentage == that.discountPercentage
                && Objects.equals(name, that.name)
                && Objects.equals(validFrom, that.validFrom)
                && Objects.equals(validTo, that.validTo)
                && Objects.equals(applicableStockItem, that.applicableStockItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, validFrom, validTo, applicableStockItem, discountPercentage);
    }

    @Override
    public String toString() {
        return "PromotionRule{" +
                "name='" + name + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", applicableStockItem=" + applicableStockItem +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
