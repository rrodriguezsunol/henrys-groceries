package uk.co.redribbondevelopment.checkout.promotions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InMemoryPromotionServiceTest {

    private InMemoryPromotionService promotionService;

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

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-13T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(10));
    }

    @Test
    void validToIsInclusive() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-03-31T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(10));
    }

    @Test
    void itemNotFoundInBasket() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(new StockItem("foo", 50));

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).isEmpty();
    }

    @Test
    void oneItemFoundThenThenDiscountIsAppliedOnce() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(10));
    }

    @Test
    void oneItemFoundWithQuantityOfThreeThenThenDiscountIsAppliedThrice() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples, 3);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).containsExactly(new Promotion(30));
    }

    @Test
    void oneItemFoundButThePromotionStartsTomorrow() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-12T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples, 3);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).isEmpty();
    }

    @Test
    void oneItemFoundButThePromotionExpiredYesterday() {
        given(mockedClock.instant()).willReturn(Instant.parse("2022-02-10T00:00:00Z"));

        promotionService = new InMemoryPromotionService(mockedStockItemService, mockedClock);

        given(mockedClock.instant()).willReturn(Instant.parse("2022-04-01T00:00:00Z"));

        Basket basketWithOneApple = new Basket();
        basketWithOneApple.addItem(apples, 3);

        Collection<Promotion> applicablePromotions = promotionService.findApplicable(basketWithOneApple);

        assertThat(applicablePromotions).isEmpty();
    }

    // TODO: test for change of month, change of year and leap years
}