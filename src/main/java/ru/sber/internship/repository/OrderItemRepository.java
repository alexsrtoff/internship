package ru.sber.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.Product;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findById(long id);
    OrderItem findOrderItemByIdAndOrderClientId(Long id, Long clientId);
    List<OrderItem> findAllByOrder_Client_Id(Long clientId);
    List<OrderItem> findAllByOrder_Id(long orderId);
    void deleteById(long id);
    Product findByProductId(long id);
}

