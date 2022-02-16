package uk.co.redribbondevelopment.checkout.promotion.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InMemoryPromotionRuleServiceTest {

    PromotionRuleService promotionService;

    // Dependencies
    private final StockItemService mockedStockItemService = mock(StockItemService.class);
    private final Clock mockedClock = mock(Clock.class);

    // Test data
    private final StockItem apples = new StockItem("apples", 100);

    @BeforeEach
    void setUpTestSubjectAndDependencies() {
        given(mockedStockItemService.findByName("apples")).willReturn(apples);
        given(mockedClock.getZone()).willReturn(ZoneId.systemDefault());
    }

    @Test
    void validFromIsInclusive() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionRuleService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-13T00:00:00Z"));

        Collection<PromotionRule> applicablePromotions = promotionService.findActiveToday();

        assertThat(applicablePromotions).containsExactly(new PromotionRule(
                "Apples have a 10% discount",
                LocalDate.parse("2022-02-13"),
                LocalDate.parse("2022-03-31"),
                apples,
                10));
    }

    @Test
    void validToIsInclusive() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionRuleService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-03-31T00:00:00Z"));

        Collection<PromotionRule> applicablePromotions = promotionService.findActiveToday();

        assertThat(applicablePromotions).containsExactly(new PromotionRule(
                "Apples have a 10% discount",
                LocalDate.parse("2022-02-13"),
                LocalDate.parse("2022-03-31"),
                apples,
                10));
    }

    @Test
    void returnsEmptyWhenThePromotionStartsTomorrow() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionRuleService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-12T00:00:00Z"));

        Collection<PromotionRule> applicablePromotions = promotionService.findActiveToday();

        assertThat(applicablePromotions).isEmpty();
    }

    @Test
    void returnsEmptyWhenThePromotionExpiredYesterday() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionRuleService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-04-01T00:00:00Z"));

        Collection<PromotionRule> applicablePromotions = promotionService.findActiveToday();

        assertThat(applicablePromotions).isEmpty();
    }

    // TODO: test for change of month, change of year and leap years
}