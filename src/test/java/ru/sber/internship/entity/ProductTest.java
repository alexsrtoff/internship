package ru.sber.internship.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProductTest {

    private Product product;

    public ProductTest() {
        this.product = new Product();
    }

    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(product);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        Product product = new Product(1L, "product", "product", BigDecimal.ONE, 1, null);
        Assert.assertNotNull(product);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        product.setId(1L);
        product.setDescription("product");
        product.setName("product");
        product.setDiscount(1);
        product.setOrderItems(null);
        product.setPrice(BigDecimal.ONE);
        Assert.assertEquals(1L, (long) product.getId());
        Assert.assertEquals(1, product.getDiscount());
        Assert.assertEquals("product", product.getDescription());
        Assert.assertEquals("product", product.getName());
        Assert.assertEquals(BigDecimal.ONE, product.getPrice());
        Assert.assertNull(product.getOrderItems());
    }

    @Test
    public void testBuilderAnnatation() {
        Product product = Product.builder()
                .id(1L)
                .price(BigDecimal.ONE)
                .name("product")
                .description("product")
                .orderItems(null)
                .discount(1)
                .build();
        Assert.assertEquals(1L, (long) product.getId());
        Assert.assertEquals(1, product.getDiscount());
        Assert.assertEquals("product", product.getDescription());
        Assert.assertEquals("product", product.getName());
        Assert.assertEquals(BigDecimal.ONE, product.getPrice());
        Assert.assertNull(product.getOrderItems());
    }
}