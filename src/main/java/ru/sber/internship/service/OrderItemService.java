package ru.sber.internship.service;

import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll();
//    OrderItem findById(long id);
//    void deleteById(int id);
    List<OrderItem> findAllByOrder(Order order);
    OrderItem save(OrderItem orderItem);

}
