package uk.co.redribbondevelopment.checkout.promotion;

import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.common.StockItemQuantity;
import uk.co.redribbondevelopment.checkout.promotion.rule.PromotionRule;
import uk.co.redribbondevelopment.checkout.promotion.rule.PromotionRuleService;

import java.util.Collection;
import java.util.List;

public final class InMemoryPromotionsEngine implements PromotionsEngine {

    private final PromotionRuleService promotionRuleService;

    public InMemoryPromotionsEngine(PromotionRuleService promotionRuleService) {
        this.promotionRuleService = promotionRuleService;
    }

    @Override
    public Collection<Promotion> findApplicable(Basket basket) {
        Collection<PromotionRule> activePromotions = promotionRuleService.findActiveToday();

        for (PromotionRule activePromo : activePromotions) {
            Collection<StockItemQuantity> itemsWithQuantities = basket.getItemsWithQuantities();

            int numberOfMatches = activePromo.getNumberOfMatches(itemsWithQuantities);
            if (numberOfMatches > 0) {
                return List.of(new Promotion(activePromo.getDiscountAmount(), numberOfMatches));
            }
        }

        return List.of();
    }
}
