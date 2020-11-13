package ru.sber.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.Product;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);

    Order findOrderByIdAndClientId(long orderId, long clientId);

    Order findByIdAndClient_Id(long orderId, long clientId);

    List<Order> findAllByClient_Id(long id);

}
