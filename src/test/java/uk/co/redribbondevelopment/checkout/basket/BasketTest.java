package uk.co.redribbondevelopment.checkout.basket;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class BasketTest {

    @Test
    void newInstanceHasTotalCostOfZero() {
        Basket newBasket = new Basket();

        assertThat(newBasket.getTotalCost()).isZero();
    }

    @Nested
    class AddItemTest {
        @Test
        void throwsExceptionWhenStockItemIsNull() {
            Basket basket = new Basket();

            NullPointerException caughtException = catchThrowableOfType(() -> basket.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }

        @Test
        void throwsExceptionWhenStockItemIsNullAndBasketHasItems() {
            Basket basket = new Basket();
            basket.addItem(new StockItem("apples", 10));

            NullPointerException caughtException = catchThrowableOfType(() -> basket.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }
    }
}