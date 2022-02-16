package uk.co.redribbondevelopment.checkout.promotion;

import uk.co.redribbondevelopment.checkout.basket.Basket;

import java.util.Collection;

public interface PromotionsEngine {

    Collection<Promotion> findApplicable(Basket basket);
}
