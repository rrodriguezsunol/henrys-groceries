package uk.co.redribbondevelopment.checkout.promotion;

public record Promotion(int singleDiscountedAmount, int timesApplicable) {

    public int getDiscountedAmount() {
        return singleDiscountedAmount * timesApplicable;
    }
}
