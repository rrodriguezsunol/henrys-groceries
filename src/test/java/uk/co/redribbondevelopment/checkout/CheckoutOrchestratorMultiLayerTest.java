package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.redribbondevelopment.checkout.promotion.InMemoryPromotionsEngine;
import uk.co.redribbondevelopment.checkout.promotion.rule.InMemoryPromotionRuleService;
import uk.co.redribbondevelopment.checkout.stock_item.InMemoryStockItemService;
import uk.co.redribbondevelopment.checkout.stock_item.ItemNotFoundException;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CheckoutOrchestratorMultiLayerTest {

    private CheckoutOrchestrator checkoutOrchestrator;

    private final Clock mockedClock = mock(Clock.class);

    @BeforeEach
    void setUpTestSubject() {
        given(mockedClock.getZone()).willReturn(Clock.systemDefaultZone().getZone());
        given(mockedClock.instant()).willReturn(Instant.now());

        StockItemService stockItemService = new InMemoryStockItemService();
        checkoutOrchestrator = new CheckoutOrchestrator(
                stockItemService,
                new InMemoryPromotionsEngine(new InMemoryPromotionRuleService(stockItemService, mockedClock)));
    }

    @Nested
    @DisplayName("Single stock item basket")
    class SingleStockItemBasketTest {

        @ParameterizedTest
        @MethodSource("itemNameAndCostProvider")
        void addSingleItemToTheBasket(String itemName, int expectedItemCost) {
            checkoutOrchestrator.addItem(itemName);

            assertThat(checkoutOrchestrator.getTotalCost()).isEqualTo(expectedItemCost);
        }

        static Stream<Arguments> itemNameAndCostProvider() {
            return Stream.of(
                    Arguments.of("soup", 65),
                    Arguments.of("bread", 80),
                    Arguments.of("milk", 130),
                    Arguments.of("apples", 10)
            );
        }

        @Test
        void throwsExceptionWhenItemNameIsNull() {
            NullPointerException caughtException = catchThrowableOfType(
                    () -> checkoutOrchestrator.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("itemName cannot be null");
        }

        @Test
        void throwsExceptionWhenItemNameIsEmpty() {
            ItemNotFoundException caughtException = catchThrowableOfType(
                    () -> checkoutOrchestrator.addItem(""), ItemNotFoundException.class);

            assertThat(caughtException).hasMessage("Item '' not found");
        }

        @Test
        void throwsExceptionWhenItemNameDoesNotExist() {
            ItemNotFoundException caughtException = catchThrowableOfType(
                    () -> checkoutOrchestrator.addItem("foobar"), ItemNotFoundException.class);

            assertThat(caughtException).hasMessage("Item 'foobar' not found");
        }
    }

    @Nested
    class SingleStockItemWithQuantityInTheBasketTest {

        @Test
        void addTwoApplesToTheBasket() {
            checkoutOrchestrator.addItem("apples");
            checkoutOrchestrator.addItem("apples");

            assertThat(checkoutOrchestrator.getTotalCost()).isEqualTo(20);
        }

        @Test
        void addThreeApplesToTheBasket() {
            checkoutOrchestrator.addItem("apples");
            checkoutOrchestrator.addItem("apples");
            checkoutOrchestrator.addItem("apples");

            assertThat(checkoutOrchestrator.getTotalCost()).isEqualTo(30);
        }
    }

    @Nested
    class MultipleStockItemsWithDifferentQuantitiesInTheBasketTest {

        @Test
        void multipleStockItems() {
            checkoutOrchestrator.addItem("soup");
            checkoutOrchestrator.addItem("bread");
            checkoutOrchestrator.addItem("apples");
            checkoutOrchestrator.addItem("bread");
            checkoutOrchestrator.addItem("soup");
            checkoutOrchestrator.addItem("bread");

            assertThat(checkoutOrchestrator.getTotalCost()).isEqualTo(380);
        }

        @Test
        void multipleStockItemsV2() {
            checkoutOrchestrator.addItem("soup", 2);
            checkoutOrchestrator.addItem("bread", 3);
            checkoutOrchestrator.addItem("apples");

            assertThat(checkoutOrchestrator.getTotalCost()).isEqualTo(380);
        }
    }

    @Nested
    class ApplesDiscountTest {

        @Test
        void sixApplesBoughtAndOneBottleOfMilkBoughtToday() {
            given(mockedClock.getZone()).willReturn(Clock.systemDefaultZone().getZone());
            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

            StockItemService stockItemService = new InMemoryStockItemService();
            CheckoutOrchestrator timeBasedCheckoutOrchestrator = new CheckoutOrchestrator(
                    stockItemService,
                    new InMemoryPromotionsEngine(
                            new InMemoryPromotionRuleService(stockItemService, mockedClock)));

            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

            timeBasedCheckoutOrchestrator.addItem("apples", 6);
            timeBasedCheckoutOrchestrator.addItem("milk");

            assertThat(timeBasedCheckoutOrchestrator.getTotalCost()).isEqualTo(190);
        }

        @Test
        void sixApplesAndOneBottleOfMilkBoughtInFiveDays() {
            given(mockedClock.getZone()).willReturn(Clock.systemDefaultZone().getZone());
            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

            StockItemService stockItemService = new InMemoryStockItemService();
            CheckoutOrchestrator timeBasedCheckoutOrchestrator = new CheckoutOrchestrator(
                    stockItemService, new InMemoryPromotionsEngine(
                            new InMemoryPromotionRuleService(stockItemService, mockedClock)));

            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-20T00:00:00Z"));

            timeBasedCheckoutOrchestrator.addItem("apples", 6);
            timeBasedCheckoutOrchestrator.addItem("milk");

            assertThat(timeBasedCheckoutOrchestrator.getTotalCost()).isEqualTo(184);
        }
    }
}