package ru.sber.internship.service;

import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll();
    OrderItem save(OrderItem orderItem);
    List<OrderItem> findAllByOrderClientId(Long clientId);
    List<OrderItem> findAllByOrder_Id(long orderId);

    OrderItem findOrderItemByIdAndOrder_Client_Id(Long id, Long clientId);


}
