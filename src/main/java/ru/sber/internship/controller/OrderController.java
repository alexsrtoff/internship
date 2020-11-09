package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderServiceImpl orderService;


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


    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody Order order) {
        if (order.getId() != null) {
            throw new IllegalArgumentException("Id found in the create reuest");
        }
        orderService.save(order);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private void update(@RequestBody Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Id not found in the create reuest");
        }
        orderService.save(order);

    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id", required = true) int id) {
        boolean flag = false;
        if (orderService.findById(id) != null) {
            orderService.deleteById(id);
            flag = true;
        }
        return flag;
    }


}
