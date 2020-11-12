package ru.sber.internship.service;

import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(long id);
    List<Order> findAllByClient(Client client);
    Order save(Order order);
    BigDecimal calcTotalPrice(Order order);

}
