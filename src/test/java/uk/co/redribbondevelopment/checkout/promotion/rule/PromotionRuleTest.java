package uk.co.redribbondevelopment.checkout.promotion.rule;

import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.common.StockItemQuantity;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionRuleTest {

    private final StockItem apples = new StockItem("apples", 100);


    @Test
    void getNumberOfMatches() {
        PromotionRule promotionRule = new PromotionRule(
                "25% discount on apples",
                LocalDate.now(),
                LocalDate.now(),
                apples,
                25);

        int numberOfMatches = promotionRule.getNumberOfMatches(List.of(new StockItemQuantity(apples, 1)));

        assertThat(numberOfMatches).isOne();
    }

    // TODO: write test for multi-product condition rule
}