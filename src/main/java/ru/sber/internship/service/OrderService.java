package ru.sber.internship.service;

import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order findById(long id);

    Order save(Order order);

    Order findOrderByIdAndAndClientId(Long orderId, Long clientId);

    boolean deleteByIdAndClient_Id(long orderId, long clientId);


}
