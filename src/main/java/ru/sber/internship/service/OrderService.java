package ru.sber.internship.service;

import ru.sber.internship.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order findById(long id);

    Order save(Order order);

    boolean deleteByIdAndClientId(long orderId, long clientId);

    List<Order> findAllByClientId(long id);

    Order findByIdAndClientId(long orderId, long clientId);


}
