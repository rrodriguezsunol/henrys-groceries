package uk.co.redribbondevelopment.checkout.promotion.rule;

import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;
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
