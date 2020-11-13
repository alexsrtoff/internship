package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.utils.OrderStatus;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.repository.ProductRepository;
import ru.sber.internship.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id);
    }


    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void calcTotalPrice(long orderId) {
        BigDecimal totalPrice = orderItemService.findAllByOrder_Id(orderId).stream()
                .map(o -> o.getProduct()
                        .getPrice()
                        .multiply(new BigDecimal(o.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = findById(orderId);
        order.setTotalPrice(totalPrice);
        save(order);
    }

    @Override
    public Order findOrderByIdAndAndClientId(Long orderId, Long clientId) {
        return orderRepository.findOrderByIdAndAndClientId(orderId, clientId);
    }

    public boolean deleteById(long id) {
        if (orderRepository.findById(id) == null) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> findAllByClient(Client client) {
        return orderRepository.findAllByClient(client);
    }

    public OrderDTO convertOrderToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public boolean chekOrder(OrderItemDTO itemDTO, Long clientId) {
        return findOrderByIdAndAndClientId(itemDTO.getOrderId(), clientId) != null;
    }

    public Order convertOrderDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .totalPrice(orderDTO.getTotalPrice())
                .orderStatus(orderDTO.getOrderStatus())
                .client(clientService.findById(orderDTO.getClientId()))
                .id(orderDTO.getId())
//                .orderItems(orderItemService.convertListOrdetDTOToListOrder(orderDTO.getItems()))
                .build();
    }

}
