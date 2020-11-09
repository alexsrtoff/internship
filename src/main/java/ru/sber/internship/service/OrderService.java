package ru.sber.internship.service;

import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.Product;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(int id);
    void deleteById(int id);
    List<Order> findAllByClient(Client client);

}
