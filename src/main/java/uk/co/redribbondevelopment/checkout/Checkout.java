package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.promotions.Promotion;
import uk.co.redribbondevelopment.checkout.promotions.PromotionService;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.util.Collection;
import java.util.Objects;

public final class Checkout {
    private final StockItemService stockItemService;
    private final PromotionService promotionService;

    private Basket basket;
    private Collection<Promotion> applicablePromotions;


    public Checkout(StockItemService stockItemService, PromotionService promotionService) {
        this.stockItemService = stockItemService;
        this.promotionService = promotionService;
    }

    public void addItem(String itemName) {
        addItem(itemName, 1);
    }

    public void addItem(String itemName, int quantity) {
        Objects.requireNonNull(itemName, "itemName cannot be null");

        var selectedStockItem = stockItemService.findByName(itemName);

        if (Objects.isNull(basket)) {
            basket = new Basket();
        }

        basket.addItem(selectedStockItem, quantity);
        applicablePromotions = promotionService.findApplicable(basket);
    }

    public int getTotalCost() {
        int totalDiscount = applicablePromotions.stream().mapToInt(Promotion::discountedAmount).sum();
        return basket.getTotalCost() - totalDiscount;
    }
}
