package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class OrderTest {

    @Test
    void newInstanceHasTotalCostOfZero() {
        Order newOrder = new Order();

        assertThat(newOrder.getTotalCost()).isZero();
    }

    @Nested
    class AddItemTest {
        @Test
        void addItemThrowsExceptionWhenStockItemIsNull() {
            Order order = new Order();

            NullPointerException caughtException = catchThrowableOfType(() -> order.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }

        @Test
        void addItemThrowsExceptionWhenStockItemIsNullAndOrderHasItems() {
            Order order = new Order();
            order.addItem(new StockItem("apples", 10));

            NullPointerException caughtException = catchThrowableOfType(() -> order.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }
    }
}