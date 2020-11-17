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

    @Autowired
    OrderItemServiceImpl orderItemService;

    /**
     * finds all clients
     * @return
     */
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * finds product by Id
     * @param id
     * @return
     */
    @Override
    public Product findById(long id) {
        return productRepository.findById(id);
    }

    /**
     * Save Product
     * @param product
     * @return
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * deletes Product by Id
     * @param id
     * @return
     */
    public boolean deleteById(long id) {
        if (productRepository.findById(id) == null) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    /**
     * converts Product to ProductDTO
     * @param product
     * @return
     */
    public ProductDTO convertProductToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    /**
     * creates a list of ProductsDTO  from the IrderItem list
     * @param items
     * @return
     */
    public List<ProductDTO> createProductDTOList(List<OrderItem> items) {

        return items.stream().map(p -> convertProductToProductDTO(p.getProduct())).collect(Collectors.toList());
    }

    /**
     * converts a list of Products to the ProductDTO list
     * @param items
     * @return
     */
    public List<ProductDTO> convertProductListToProductDTOList(List<Product> items) {

        return items.stream().map(p -> convertProductToProductDTO(p)).collect(Collectors.toList());
    }

    /**
     * converts a list of ProductDTO to the Products list
     * @param productDTO
     * @return
     */
    public Product convertProductDTOToProduct(ProductDTO productDTO) {
        return save(Product.builder()
                .id(productDTO.getId())
                .description(productDTO.getDescription())
                .discount(productDTO.getDiscount())
                .name(productDTO.getName())
                .orderItems(orderItemService.findAllByProductId(productDTO.getId()))
                .price(productDTO.getPrice())
                .build());
    }
}
