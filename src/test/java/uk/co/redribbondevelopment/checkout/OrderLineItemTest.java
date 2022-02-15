package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class OrderLineItemTest {

    private final StockItem onePenceStockItem = new StockItem("foo", 1);
    @Test
    void constructorThrowsExceptionWhenStockItemIsNull() {
        NullPointerException caughtException = catchThrowableOfType(
                () -> new OrderLineItem(null), NullPointerException.class);

        assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
    }

    @Test
    void newInstanceStartsWithQuantityOfOne() {
        var orderLineItem = new OrderLineItem(onePenceStockItem);

        assertThat(orderLineItem.getQuantity()).isEqualTo(1);
    }

    @Nested
    class OrderLineTotalTest {

        @Test
        void returnsTheCostOfOneUnitWhenTheQuantityIsOne() {
            var orderLineItem = new OrderLineItem(onePenceStockItem);

            assertThat(orderLineItem.getLineTotal()).isEqualTo(1);
        }

        @Test
        void returnsTheCostOfTwoUnitsWhenTheQuantityIsTwo() {
            var orderLineItem = new OrderLineItem(onePenceStockItem);
            orderLineItem.incrementQuantity();

            assertThat(orderLineItem.getLineTotal()).isEqualTo(2);
        }
    }
}