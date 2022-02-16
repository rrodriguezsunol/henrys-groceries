package uk.co.redribbondevelopment.checkout.promotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.promotion.rule.PromotionRule;
import uk.co.redribbondevelopment.checkout.promotion.rule.PromotionRuleService;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InMemoryPromotionsEngineTest {

    private InMemoryPromotionsEngine promotionsEngine;

    // Dependencies
    private final PromotionRuleService mockedPromotionRuleService = mock(PromotionRuleService.class);

    // Test data
    private final StockItem apples = new StockItem("apples", 100);

    @BeforeEach
    void setUpTestSubject() {
        promotionsEngine = new InMemoryPromotionsEngine(mockedPromotionRuleService);
    }

    @Test
    void activePromotionFoundButItemIsNotInTheBasket() {
        given(mockedPromotionRuleService.findActiveToday()).willReturn(List.of(new PromotionRule(
                "25% discount on apples",
                LocalDate.now(),
                LocalDate.now(),
                apples, 25)));

        Basket basketWithNoApples = new Basket();
        basketWithNoApples.addItem(new StockItem("foo", 50));

        Collection<Promotion> applicablePromotions = promotionsEngine.findApplicable(basketWithNoApples);

        assertThat(applicablePromotions).isEmpty();
    }

    @Test
    void activePromotionFoundAndOneItemIsFoundInTheBasketThenTheDiscountIsAppliedOnce() {
        given(mockedPromotionRuleService.findActiveToday()).willReturn(List.of(new PromotionRule(
                "25% discount on apples",
                LocalDate.now(),
                LocalDate.now(),
                apples, 25)));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples);

        Collection<Promotion> applicablePromotions = promotionsEngine.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(25));
    }

    @Test
    void activePromotionFoundAndOneItemIsFoundInTheBasketThenTheDiscountIsAppliedThrice() {
        given(mockedPromotionRuleService.findActiveToday()).willReturn(List.of(new PromotionRule(
                "25% discount on apples",
                LocalDate.now(),
                LocalDate.now(),
                apples, 25)));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples, 3);

        Collection<Promotion> applicablePromotions = promotionsEngine.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(75));
    }

    @Test
    void basketWithItemsButThereAreNoActivePromotionsToday() {
        given(mockedPromotionRuleService.findActiveToday()).willReturn(List.of());

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples, 3);

        Collection<Promotion> applicablePromotions = promotionsEngine.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).isEmpty();
    }
}