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
        NullPointerException caughtException = catchThrowableOfType(
                () -> new BasketLineItem(null), NullPointerException.class);

        assertThat(caughtException).hasMessage("selectedStockItem cannot be null");
    }

    @Test
    void newInstanceStartsWithQuantityOfOne() {
        var basketLineItem = new BasketLineItem(onePenceStockItem);

        assertThat(basketLineItem.getQuantity()).isEqualTo(1);
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