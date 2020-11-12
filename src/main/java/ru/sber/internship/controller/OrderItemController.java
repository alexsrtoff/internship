package ru.sber.internship.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.Product;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/items")
public class OrderItemController {

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    ProductServiceImpl productService;

    @GetMapping
    public List<OrderItem> findAll() {
        return orderItemService.findAll();
    }


    @GetMapping("/{id}")
    public OrderItem findById(@PathVariable(value = "id", required = true) int id) {
        if (orderItemService.findById(id) != null) {
            return orderItemService.findById(id);
        } else return new OrderItem();
    }


    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public OrderItem add(@RequestBody OrderItemDTO itemDTO) {
        System.out.println(itemDTO);
        OrderItem item = OrderItem.builder()
                .id(itemDTO.getId())
                .count(itemDTO.getCount())
                .product(productService.findById(itemDTO.getProductId()))
                .build();
        return orderItemService.save(item);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private OrderItem update(@RequestBody OrderItem orderItem) {
        if (orderItem.getId() == null) {
            throw new IllegalArgumentException("Id not found in the create reuest");
        }
       return orderItemService.save(orderItem);
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id", required = true) int id) {
        return orderItemService.deleteById(id);
    }




//    @PostMapping(value = "/add1", consumes = "application/json", produces = "application/json")
//    public void  add1(@RequestBody Map<String, Object> map) {
//        ObjectMapper mapper = new ObjectMapper();
//        String json = String.valueOf(map.get("product"));
//        try {
//            Product product = mapper.readValue(json, Product.class);
//            System.out.println("product: " + product);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        map.forEach((k, v) -> System.out.printf("key: %s , value: %s", k, v).println());
//    }

}
