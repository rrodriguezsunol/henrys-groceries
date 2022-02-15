package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.redribbondevelopment.checkout.stock_item.InMemoryStockItemService;
import uk.co.redribbondevelopment.checkout.stock_item.ItemNotFoundException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class CheckoutProcessTest {

    private final Checkout checkout = new Checkout(new InMemoryStockItemService());

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
    class SingleStockItemWithQuantityBasketTest {

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

    // Todo: handle multiple different items in the same basket
}