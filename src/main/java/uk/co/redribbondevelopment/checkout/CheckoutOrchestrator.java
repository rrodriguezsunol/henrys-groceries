package uk.co.redribbondevelopment.checkout;

import uk.co.redribbondevelopment.checkout.basket.Basket;
import uk.co.redribbondevelopment.checkout.promotion.Promotion;
import uk.co.redribbondevelopment.checkout.promotion.PromotionsEngine;
import uk.co.redribbondevelopment.checkout.stock_item.StockItemService;

import java.util.Collection;
import java.util.Objects;

public final class CheckoutOrchestrator {
    private final StockItemService stockItemService;
    private final PromotionsEngine promotionsEngine;

    private Basket basket;
    private Collection<Promotion> applicablePromotions;


    public CheckoutOrchestrator(StockItemService stockItemService, PromotionsEngine promotionsEngine) {
        this.stockItemService = stockItemService;
        this.promotionsEngine = promotionsEngine;
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
        applicablePromotions = promotionsEngine.findApplicable(basket);
    }

    public int getTotalCost() {
        int totalDiscount = applicablePromotions.stream().mapToInt(Promotion::discountedAmount).sum();
        return basket.getTotalCost() - totalDiscount;
    }
}
