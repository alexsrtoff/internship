package ru.sber.internship.entity.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProductDTOTest {

    ProductDTO productDTO;

    public ProductDTOTest() {
        this.productDTO = new ProductDTO();
    }

    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(productDTO);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        ProductDTO productDTO = new ProductDTO(1L, "product", "product", BigDecimal.ONE, 1);
        Assert.assertNotNull(productDTO);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        productDTO.setId(1L);
        productDTO.setDescription("product");
        productDTO.setName("product");
        productDTO.setDiscount(1);
        productDTO.setPrice(BigDecimal.ONE);
        Assert.assertEquals(1L, (long) productDTO.getId());
        Assert.assertEquals(1, productDTO.getDiscount());
        Assert.assertEquals("product", productDTO.getDescription());
        Assert.assertEquals("product", productDTO.getName());
        Assert.assertEquals(BigDecimal.ONE, productDTO.getPrice());
    }

    @Test
    public void testBuilderAnnatation() {
        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .description("product")
                .discount(1)
                .name("product")
                .price(BigDecimal.ONE)
                .build();
        Assert.assertEquals(1L, (long) productDTO.getId());
        Assert.assertEquals(1, productDTO.getDiscount());
        Assert.assertEquals("product", productDTO.getDescription());
        Assert.assertEquals("product", productDTO.getName());
        Assert.assertEquals(BigDecimal.ONE, productDTO.getPrice());
    }
}