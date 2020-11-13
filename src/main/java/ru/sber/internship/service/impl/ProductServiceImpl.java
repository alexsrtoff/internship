package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.Product;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.repository.ProductRepository;
import ru.sber.internship.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public boolean deleteById(long id) {
        if (productRepository.findById(id) == null) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    public ProductDTO convertProductToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public List<ProductDTO> createProductDTOList(List<OrderItem> items) {

        return items.stream().map(p -> convertProductToProductDTO(p.getProduct())).collect(Collectors.toList());
    }
}
