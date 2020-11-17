package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.utils.OrderStatus;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    /**
     * finds all Orders
     *
     * @return
     */
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * finds Order by Id
     *
     * @param id
     * @return
     */
    @Override
    public Order findById(long id) {
        return orderRepository.findById(id);
    }

    /**
     * Save order to database
     *
     * @param order
     * @return
     */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * calculates the total price of the Order
     *
     * @param orderId
     */
    public void calcTotalPrice(long orderId) {
        BigDecimal totalPrice = orderItemService.findAllByOrderId(orderId).stream()
                .map(o -> o.getProduct()
                        .getPrice()
                        .multiply(new BigDecimal(o.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = findById(orderId);
        order.setTotalPrice(totalPrice);
        save(order);
    }

    /**
     * deletes Order by Id and Client Id
     *
     * @param orderId
     * @param clientId
     * @return
     */
    @Override
    public boolean deleteByIdAndClientId(long orderId, long clientId) {
        Order order = findByIdAndClientId(orderId, clientId);
        if (order == null) {
            return false;
        }
        orderRepository.delete(order);
        return true;

    }

    /**
     * finds all Orders by Client
     *
     * @param id
     * @return
     */
    @Override
    public List<Order> findAllByClientId(long id) {
        return orderRepository.findAllByClientId(id);
    }

    /**
     * finds Order by Client
     *
     * @param orderId
     * @param clientId
     * @return
     */
    @Override
    public Order findByIdAndClientId(long orderId, long clientId) {
        return orderRepository.findOrderByIdAndClientId(orderId, clientId);
    }

    /**
     * deletes Order by Id
     *
     * @param id
     * @return
     */
    public boolean deleteById(long id) {
        if (orderRepository.findById(id) == null) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    /**
     * converts Order to OrderDTO
     *
     * @param order
     * @return
     */
    public OrderDTO convertOrderToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .clientId(order.getClient().getId())
                .build();
    }

    /**
     * checks whether the Order belongs to the Client by OrderItem
     *
     * @param itemDTO
     * @param clientId
     * @return
     */
    public boolean chekOrderByOrderItem(OrderItemDTO itemDTO, Long clientId) {
        Order order = findByIdAndClientId(itemDTO.getOrderId(), clientId);
        return order != null && order.getOrderStatus().equals(OrderStatus.UNPAYED);
    }

    /**
     * checks whether the Order belongs to the Client by Order
     *
     * @param orderDTO
     * @return
     */
    public boolean chekOrder(OrderDTO orderDTO) {
        Order order = findByIdAndClientId(orderDTO.getId(), orderDTO.getClientId());
        return order != null && order.getOrderStatus().equals(OrderStatus.UNPAYED);
    }


    /**
     * converts OrderDTO to Order
     *
     * @param orderDTO
     * @return
     */
    public Order convertOrderDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .totalPrice(orderDTO.getTotalPrice())
                .orderStatus(orderDTO.getOrderStatus())
                .client(clientService.findById(orderDTO.getClientId()))
                .id(orderDTO.getId())
                .build();
    }

    /**
     * converts list of Orders to the list of OrderDTO
     *
     * @param orderList
     * @return
     */
    public List<OrderDTO> convertOrderListToOrderDTOList(List<Order> orderList) {
        return orderList.stream().map(o -> convertOrderToOrderDTO(o)).collect(Collectors.toList());
    }

    /**
     * creates Order by orderItem and Client
     *
     * @param itemDTO
     * @param clientId
     * @return
     */
    public Order createOrder(OrderItemDTO itemDTO, Long clientId) {
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = save(Order.builder()
                .client(clientService.findById(clientId))
                .orderStatus(OrderStatus.UNPAYED)
                .build());
        itemDTO.setOrderId(order.getId());
        orderItems.add(orderItemService.convertOrderItemDTOToOrderItem(itemDTO));

        order.setOrderItems(orderItems);
        calcTotalPrice(order.getId());
        return order;
    }

    /**
     * updates Order
     *
     * @param orderDTO
     * @return
     */
    public OrderDTO update(OrderDTO orderDTO) {
        Order order = findById(orderDTO.getId());
        order.setOrderStatus(orderDTO.getOrderStatus());
        return convertOrderToOrderDTO(save(order));
    }
}
