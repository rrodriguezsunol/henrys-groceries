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
        private final Basket basket = new Basket();

        // Test Data
        private final StockItem apples = new StockItem("apples", 10);

        @Test
        void incrementsQuantityByOneWhenItemNameMatchesQuantityIsNotSpecified() {
            Basket basket = new Basket();
            basket.addItem(apples);

            assertThat(basket.getQuantityOf(apples)).isOne();

            basket.addItem(apples);
            assertThat(basket.getQuantityOf(apples)).isEqualTo(2);

            basket.addItem(apples);
            assertThat(basket.getQuantityOf(apples)).isEqualTo(3);
        }

        @Test
        void addsItemWithSpecifiedQuantityToTheBasket() {
            Basket basket = new Basket();
            basket.addItem(apples, 10);

            assertThat(basket.getQuantityOf(apples)).isEqualTo(10);
        }

        @Test
        void addingTheSameItemTwiceWithDifferentQuantitiesAddsThemUpToTheBasket() {
            basket.addItem(apples, 10);
            basket.addItem(apples, 5);

            assertThat(basket.getQuantityOf(apples)).isEqualTo(15);
        }

        @Test
        void throwsExceptionWhenStockItemIsNull() {
            Basket basket = new Basket();

            NullPointerException caughtException = catchThrowableOfType(() -> basket.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }

        @Test
        void throwsExceptionWhenStockItemIsNullAndBasketHasItems() {
            Basket basket = new Basket();
            basket.addItem(apples);

            NullPointerException caughtException = catchThrowableOfType(() -> basket.addItem(null), NullPointerException.class);

            assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
        }

        @Test
        void throwsExceptionWhenQuantityIsZero() {
            var caughtException = catchThrowableOfType(() -> basket.addItem(apples, 0), IllegalArgumentException.class);

            assertThat(caughtException).hasMessage("quantity cannot be less than 1. quantity = 0");
        }
    }
}