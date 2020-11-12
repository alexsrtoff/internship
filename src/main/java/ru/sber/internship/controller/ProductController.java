package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Product;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;


    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }


    @GetMapping("/{id}")
    public Product findById(@PathVariable(value = "id", required = true) int id) {
        if (productService.findById(id) != null) {
            return productService.findById(id);
        } else return new Product();
    }


    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("Id found in the create reuest");
        }
        productService.save(product);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private void update(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Id not found in the create reuest");
        }
        productService.save(product);

    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id", required = true) long id) {
        return productService.deleteById(id);
    }

}
