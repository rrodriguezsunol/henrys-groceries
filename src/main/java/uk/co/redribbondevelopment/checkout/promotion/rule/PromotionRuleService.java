package uk.co.redribbondevelopment.checkout.promotion.rule;

import java.util.Collection;

public interface PromotionRuleService {

    Collection<PromotionRule> findActiveToday();
}
