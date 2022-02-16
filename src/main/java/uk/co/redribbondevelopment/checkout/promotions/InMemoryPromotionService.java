package uk.co.redribbondevelopment.checkout.promotions;

import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public final class InMemoryPromotionService implements PromotionService {

    private final Clock clock;

    private final PromotionRule promotionRule;

    public InMemoryPromotionService(StockItemService stockItemService, Clock clock) {
        this.clock = clock;
        var now = LocalDate.now(clock);

        promotionRule = new PromotionRule(
                "Apples have a 10% discount",
                now.plusDays(3),
                now.plusMonths(1).withDayOfMonth(now.plusMonths(1).lengthOfMonth()),
                stockItemService.findByName("apples"),
                10);
    }

    @Override
    public Collection<Promotion> findApplicable(Basket basket) {
        var today = LocalDate.now(clock);

        if ((today.isEqual(promotionRule.validFrom) || today.isAfter(promotionRule.validFrom))
                && (today.isEqual(promotionRule.validTo) || today.isBefore(promotionRule.validTo))) {

            var quantity = basket.getQuantityOf(promotionRule.applicableStockItem);

            if (quantity != 0) {
                var unitPrice = promotionRule.applicableStockItem.cost();

                var totalPriceBeforeDiscount = quantity * unitPrice;
                var amountDiscounted = (totalPriceBeforeDiscount * promotionRule.discountPercentage) / 100;

                return List.of(new Promotion(amountDiscounted));
            }
        }

        return List.of();
    }
}
