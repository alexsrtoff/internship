package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.repository.ProductRepository;
import ru.sber.internship.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public boolean deleteByIdAndClient_Id(long orderId, long clientId) {
        Order order = orderRepository.findByIdAndAndClient_Id(orderId, clientId);
        if (order == null) {
            return false;
        }
        orderRepository.delete(order);
        return true;

    }

    public boolean deleteById(long id) {
        if (orderRepository.findById(id) == null) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }


    public OrderDTO convertOrderToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .clientId(order.getClient().getId())
                .build();
    }

    public boolean chekOrderByOrderItem(OrderItemDTO itemDTO, Long clientId) {
        return findOrderByIdAndAndClientId(itemDTO.getOrderId(), clientId) != null;
    }

    public boolean chekOrder(OrderDTO orderDTO) {
        return findOrderByIdAndAndClientId(orderDTO.getId(), orderDTO.getClientId()) != null;
    }


    public Order convertOrderDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .totalPrice(orderDTO.getTotalPrice())
                .orderStatus(orderDTO.getOrderStatus())
                .client(clientService.findById(orderDTO.getClientId()))
                .id(orderDTO.getId())
                .build();
    }

    public List<OrderDTO> convertOrderListToOrderDTOList(List<Order> orderList) {
        return orderList.stream().map(o -> convertOrderToOrderDTO(o)).collect(Collectors.toList());
    }
}
