package ru.sber.internship.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.Product;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void findAll() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build());
        products.add(Product.builder().name("product1").price(BigDecimal.ONE).description("product1").discount(0).id(1L).build());
        Mockito.when(productService.findAll()).thenReturn(products);
        List<Product> productList = productService.findAll();
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(products, productList);
    }

    @Test
    void findById() {
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Mockito.when(productService.findById(1L)).thenReturn(product);
        Product product1 = productService.findById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Assert.assertEquals(product, product1);
    }

    @Test
    void save() {
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Mockito.when(productService.save(product)).thenReturn(product);
        Product product1 = productService.save(product);
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
        Assert.assertEquals(product, product1);
    }

    @Test
    void deleteById() {
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Mockito.when(productService.findById(1L)).thenReturn(product);
        boolean deleteTrue = productService.deleteById(1L);
        Assert.assertTrue(deleteTrue);
        Mockito.when(productService.findById(10L)).thenReturn(null);
        boolean deleteFalse = productService.deleteById(10L);
        Assert.assertFalse(deleteFalse);
    }

    @Test
    void convertProductToProductDTO() {
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .build();
        ProductDTO convertedProduct = productService.convertProductToProductDTO(product);
        Assert.assertEquals(productDTO, convertedProduct);
    }

    @Test
    void convertProductListToProductDTOList() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Product product1 = Product.builder().name("product1").price(BigDecimal.ONE).description("product1").discount(0).id(1L).build();
        products.add(product);
        products.add(product1);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productService.convertProductToProductDTO(product));
        productDTOList.add(productService.convertProductToProductDTO(product1));
        List<ProductDTO> convertdeProductList = productService.convertProductListToProductDTOList(products);
        Assert.assertEquals(productDTOList, convertdeProductList);
    }

    @Test
    void createProductDTOList() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Product product1 = Product.builder().name("product1").price(BigDecimal.ONE).description("product1").discount(0).id(1L).build();
        products.add(product);
        products.add(product1);
        List<ProductDTO> productDTOS = productService.convertProductListToProductDTOList(products);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().id(1L).count(1).order(null).product(product).build());
        orderItems.add(OrderItem.builder().id(2L).count(2).order(null).product(product1).build());
        List<ProductDTO> productDTOList = productService.createProductDTOList(orderItems);
        Assert.assertEquals(productDTOS, productDTOList);
    }


    @Test
    void convertProductDTOToProduct() {
        ProductDTO productDTO = ProductDTO.builder().id(1L).name("product").price(BigDecimal.ONE).discount(0).description("product").build();
        Product product = Product.builder().name("product").price(BigDecimal.ONE).description("product").discount(0).id(1L).build();
        Mockito.when(productService.convertProductDTOToProduct(productDTO)).thenReturn(product);
        Product convertedProduct = productService.convertProductDTOToProduct(productDTO);
        Assert.assertEquals(convertedProduct, product);
    }
}