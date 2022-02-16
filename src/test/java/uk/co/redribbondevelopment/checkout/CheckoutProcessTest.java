package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.redribbondevelopment.checkout.promotions.InMemoryPromotionService;
import uk.co.redribbondevelopment.checkout.promotions.PromotionService;
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

class CheckoutProcessTest {

    private final Checkout checkout = new Checkout(new InMemoryStockItemService(), mock(PromotionService.class));

    @Nested
    @DisplayName("Single stock item basket")
    class SingleStockItemBasketTest {

        @ParameterizedTest
        @MethodSource("itemNameAndCostProvider")
        void addSingleItemToTheBasket(String itemName, int expectedItemCost) {
            checkout.addItem(itemName);

            assertThat(checkout.getTotalCost()).isEqualTo(expectedItemCost);
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
                    () -> checkout.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("itemName cannot be null");
        }

        @Test
        void throwsExceptionWhenItemNameIsEmpty() {
            ItemNotFoundException caughtException = catchThrowableOfType(
                    () -> checkout.addItem(""), ItemNotFoundException.class);

            assertThat(caughtException).hasMessage("Item '' not found");
        }

        @Test
        void throwsExceptionWhenItemNameDoesNotExist() {
            ItemNotFoundException caughtException = catchThrowableOfType(
                    () -> checkout.addItem("foobar"), ItemNotFoundException.class);

            assertThat(caughtException).hasMessage("Item 'foobar' not found");
        }
    }

    @Nested
    class SingleStockItemWithQuantityInTheBasketTest {

        @Test
        void addTwoApplesToTheBasket() {
            checkout.addItem("apples");
            checkout.addItem("apples");

            assertThat(checkout.getTotalCost()).isEqualTo(20);
        }

        @Test
        void addThreeApplesToTheBasket() {
            checkout.addItem("apples");
            checkout.addItem("apples");
            checkout.addItem("apples");

            assertThat(checkout.getTotalCost()).isEqualTo(30);
        }
    }

    @Nested
    class MultipleStockItemsWithDifferentQuantitiesInTheBasketTest {

        @Test
        void multipleStockItems() {
            checkout.addItem("soup");
            checkout.addItem("bread");
            checkout.addItem("apples");
            checkout.addItem("bread");
            checkout.addItem("soup");
            checkout.addItem("bread");

            assertThat(checkout.getTotalCost()).isEqualTo(380);
        }

        @Test
        void multipleStockItemsV2() {
            checkout.addItem("soup", 2);
            checkout.addItem("bread", 3);
            checkout.addItem("apples");

            assertThat(checkout.getTotalCost()).isEqualTo(380);
        }
    }

    @Nested
    class ApplesDiscountTest {
        Clock mockedClock = mock(Clock.class);

        @Test
        void applesDiscount() {
            given(mockedClock.getZone()).willReturn(Clock.systemDefaultZone().getZone());
            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-15T00:00:00Z"));

            StockItemService stockItemService = new InMemoryStockItemService();
            Checkout timeBasedCheckout = new Checkout(stockItemService, new InMemoryPromotionService(stockItemService, mockedClock));

            given(mockedClock.instant()).willReturn(Instant.parse("2022-02-20T00:00:00Z"));

            timeBasedCheckout.addItem("apples", 6);
            timeBasedCheckout.addItem("milk");

            assertThat(timeBasedCheckout.getTotalCost()).isEqualTo(184);
        }
    }
}