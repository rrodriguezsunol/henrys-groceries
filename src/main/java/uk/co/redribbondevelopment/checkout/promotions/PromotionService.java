package uk.co.redribbondevelopment.checkout.promotions;

import uk.co.redribbondevelopment.checkout.basket.Basket;

import java.util.Collection;

public interface PromotionService {

    Collection<Promotion> findApplicable(Basket basket);
}
