package uk.co.redribbondevelopment.checkout;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutProcessTest {

    @Test
    void checkout() {
        var checkout = Checkout.startNew();

        checkout.addItem("apple");

        assertThat(checkout.getTotalCost()).isEqualTo(10);
    }
}