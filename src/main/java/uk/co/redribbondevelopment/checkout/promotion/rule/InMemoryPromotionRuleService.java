package uk.co.redribbondevelopment.checkout.promotion.rule;

import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InMemoryPromotionRuleService implements PromotionRuleService {
    private final Clock clock;

    private final PromotionRule promotionRule;

    public InMemoryPromotionRuleService(StockItemService stockItemService, Clock clock) {
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
    public Collection<PromotionRule> findActiveToday() {
        var today = LocalDate.now(clock);

        if ((today.isEqual(promotionRule.getValidFrom()) || today.isAfter(promotionRule.getValidFrom()))
                && (today.isEqual(promotionRule.getValidTo()) || today.isBefore(promotionRule.getValidTo()))) {
            return List.of(promotionRule);
        }

        return List.of();
    }
}
