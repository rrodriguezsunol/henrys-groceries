package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class CheckoutProcessTest {

    private final Checkout checkout = Checkout.startNew();

    @ParameterizedTest
    @MethodSource("productNameAndCostProvider")
    void addSingleProductToBasket(String productName, int expectedProductCost) {
        checkout.addItem(productName);

        assertThat(checkout.getTotalCost()).isEqualTo(expectedProductCost);
    }

    static Stream<Arguments> productNameAndCostProvider() {
        return Stream.of(
                Arguments.of("soup", 65),
                Arguments.of("bread", 80),
                Arguments.of("milk", 130),
                Arguments.of("apples", 10)
        );
    }

    @Test
    void throwsExceptionWhenProductNameIsNull() {
        NullPointerException caughtException = catchThrowableOfType(
                () -> checkout.addItem(null), NullPointerException.class);

        assertThat(caughtException).hasMessage("productName cannot be null");
    }

    @Test
    void throwsExceptionWhenProductNameIsEmpty() {
        ProductNotFoundException caughtException = catchThrowableOfType(
                () -> checkout.addItem(""), ProductNotFoundException.class);

        assertThat(caughtException).hasMessage("Product '' not found");
    }

    @Test
    void throwsExceptionWhenProductNameDoesNotExist() {
        ProductNotFoundException caughtException = catchThrowableOfType(
                () -> checkout.addItem("foobar"), ProductNotFoundException.class);

        assertThat(caughtException).hasMessage("Product 'foobar' not found");
    }
}