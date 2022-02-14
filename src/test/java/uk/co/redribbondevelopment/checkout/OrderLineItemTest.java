package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.product.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class OrderLineItemTest {

    private final Product onePenceProduct = new Product("foo", 1);
    @Test
    void constructorThrowsExceptionWhenProductIsNull() {
        NullPointerException caughtException = catchThrowableOfType(
                () -> new OrderLineItem(null), NullPointerException.class);

        assertThat(caughtException).hasMessage("selectedProduct cannot be null");
    }

    @Test
    void newInstanceStartsWithQuantityOfOne() {
        var orderLineItem = new OrderLineItem(onePenceProduct);

        assertThat(orderLineItem.getQuantity()).isEqualTo(1);
    }

    @Nested
    class OrderLineTotalTest {

        @Test
        void returnsTheCostOfOneUnitWhenTheQuantityIsOne() {
            var orderLineItem = new OrderLineItem(onePenceProduct);

            assertThat(orderLineItem.getLineTotal()).isEqualTo(1);
        }

        @Test
        void returnsTheCostOfTwoUnitsWhenTheQuantityIsTwo() {
            var orderLineItem = new OrderLineItem(onePenceProduct);
            orderLineItem.incrementQuantity();

            assertThat(orderLineItem.getLineTotal()).isEqualTo(2);
        }
    }
}