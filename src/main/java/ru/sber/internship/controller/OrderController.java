package ru.sber.internship.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    OrderItemServiceImpl orderItemService;


    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }


    @GetMapping("/{id}")
    public Order findById(@PathVariable(value = "id", required = true) int id) {
        if (orderService.findById(id) != null) {
            return orderService.findById(id);
        } else return new Order();
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private Order update(@RequestBody Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update reuest");
        }
        order.setTotalPrice(orderService.calcTotalPrice(order));
        return orderService.save(order);

    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id", required = true) long id) {
        return orderService.deleteById(id);
    }

    @PostMapping(value = "/{clientId}/add", consumes = "application/json", produces = "application/json")
    public Order add(@RequestBody String json, @PathVariable("clientId") long clientId) {
        Order order = new Order();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            order = mapper.treeToValue(node.get("order"), Order.class);
            List<OrderItem> orderItems = orderItemService.transferOrderItemsDTOToOrderItems(node, mapper, order);
            order.setClient(clientService.findById(clientId));
            order.setOrderItems(orderItems);
            order.setTotalPrice(orderService.calcTotalPrice(order));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orderService.save(order);
    }


}
