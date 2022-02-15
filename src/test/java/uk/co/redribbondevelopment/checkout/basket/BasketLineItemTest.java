package uk.co.redribbondevelopment.checkout.basket;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.redribbondevelopment.checkout.stock_item.StockItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class BasketLineItemTest {

    private final StockItem onePenceStockItem = new StockItem("foo", 1);

    @Test
    void constructorThrowsExceptionWhenStockItemIsNull() {
        var caughtException = catchThrowableOfType(
                () -> new BasketLineItem(null), NullPointerException.class);

        assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
    }

    @Test
    void constructorThrowsExceptionWhenQuantityIsZero() {
        var caughtException = catchThrowableOfType(
                () -> new BasketLineItem(onePenceStockItem, 0), IllegalArgumentException.class);

        assertThat(caughtException).hasMessage("quantity cannot be less than 1. quantity = 0");
    }

    @Test
    void constructorThrowsExceptionWhenQuantityIsNegative() {
        var caughtException = catchThrowableOfType(
                () -> new BasketLineItem(onePenceStockItem, -1), IllegalArgumentException.class);

        assertThat(caughtException).hasMessage("quantity cannot be less than 1. quantity = -1");
    }

    @Test
    void constructorThrowsExceptionWhenQuantityIsGreaterThan999() {
        var caughtException = catchThrowableOfType(
                () -> new BasketLineItem(onePenceStockItem, 1000), IllegalArgumentException.class);

        assertThat(caughtException).hasMessage("quantity cannot be greater than 999. quantity = 1000");
    }

    @Test
    void newInstanceStartsWithQuantityOfOne() {
        var basketLineItem = new BasketLineItem(onePenceStockItem);

        assertThat(basketLineItem.getQuantity()).isEqualTo(1);
    }

    @Test
    void newInstanceStartsWithQuantityOf999() {
        var basketLineItem = new BasketLineItem(onePenceStockItem, 999);

        assertThat(basketLineItem.getQuantity()).isEqualTo(999);
    }

    @Nested
    class SetQuantityTest {

        @Test
        void setsQuantityToNewOne() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            basketLineItem.setQuantity(5);

            assertThat(basketLineItem.getLineTotal()).isEqualTo(5);
        }

        @Test
        void setsQuantityTo999() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            basketLineItem.setQuantity(999);

            assertThat(basketLineItem.getLineTotal()).isEqualTo(999);
        }

        @Test
        void throwsExceptionWhenQuantityIsZero() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            var caughtException = catchThrowableOfType(() -> basketLineItem.setQuantity(0), IllegalArgumentException.class);

            assertThat(caughtException).hasMessage("quantity cannot be less than 1. quantity = 0");
        }

        @Test
        void throwsExceptionWhenQuantityIsNegative() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            var caughtException = catchThrowableOfType(() -> basketLineItem.setQuantity(-1), IllegalArgumentException.class);

            assertThat(caughtException).hasMessage("quantity cannot be less than 1. quantity = -1");
        }

        @Test
        void throwsExceptionWhenQuantityIsGreaterThan999() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            var caughtException = catchThrowableOfType(() -> basketLineItem.setQuantity(1000), IllegalArgumentException.class);

            assertThat(caughtException).hasMessage("quantity cannot be greater than 999. quantity = 1000");
        }
    }

    @Nested
    class BasketLineTotalTest {

        @Test
        void returnsTheCostOfOneUnitWhenTheQuantityIsOne() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);

            assertThat(basketLineItem.getLineTotal()).isEqualTo(1);
        }

        @Test
        void returnsTheCostOfTwoUnitsWhenTheQuantityIsTwo() {
            var basketLineItem = new BasketLineItem(onePenceStockItem);
            basketLineItem.incrementQuantity();

            assertThat(basketLineItem.getLineTotal()).isEqualTo(2);
        }
    }
}