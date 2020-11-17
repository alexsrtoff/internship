package ru.sber.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.internship.entity.OrderItem;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findById(long id);

    OrderItem findOrderItemByIdAndOrderClientId(Long id, Long clientId);

    List<OrderItem> findAllByOrderClientId(Long clientId);

    List<OrderItem> findAllByOrderId(long orderId);

    void deleteById(long id);

    List<OrderItem> findAllByProductId(long productId);
}

