package ru.sber.internship.service;

import ru.sber.internship.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(long id);
    Product save(Product product);
}
