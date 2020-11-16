package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Product;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = {"application/json", "application/xml"})
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;


    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.convertProductListToProductDTOList(productService.findAll());
    }


    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable(value = "id") int id) {
        Product product = productService.findById(id);
        if (product != null) {
            return productService.convertProductToProductDTO(product);
        } else return new ProductDTO();
    }

    @Transactional
    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    public ProductDTO add(@RequestBody ProductDTO productDTO) {
        Product product = productService.findById(productDTO.getId());
        if (product != null) {
            return new ProductDTO();
        }
        Product newProduct = productService.convertProductDTOToProduct(productDTO);
        productDTO.setId(newProduct.getId());
        return productDTO;
    }

    @Transactional
    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    public ProductDTO update(@RequestBody ProductDTO productDTO) {
        Product product = productService.findById(productDTO.getId());
        if (product == null) {
            return new ProductDTO();
        }
        productService.convertProductDTOToProduct(productDTO);
        return productDTO;
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") long id) {
        return productService.deleteById(id);
    }

}
