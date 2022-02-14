package uk.co.redribbondevelopment.checkout.product;

import java.util.List;

public class InMemoryProductService implements ProductService {

    private final List<Product> productDefinitions = List.of(
            new Product("soup", 65),
            new Product("bread", 80),
            new Product("milk", 130),
            new Product("apples", 10)
    );

    @Override
    public Product findByName(String name) {
        return productDefinitions.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(name));
    }
}
