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
import java.util.ArrayList;
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
//    @Override
//    public BigDecimal calcTotalPrice(Order order) {
//        return order.getOrderItems().stream().map(orderItem -> orderItem
//                .getProduct()
//                .getPrice()
//                .multiply(new BigDecimal(orderItem.getCount())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
////        return order.getOrderItems().stream().
////                map(p -> p.getProduct()
////                        .getPrice()
////                        .multiply(new BigDecimal(p.getCount()))).
////                reduce(BigDecimal.ZERO, BigDecimal::add);
////
//    }

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


    public Order getOrder(OrderItemDTO itemDTO) {
        List<OrderItem> orderItems = orderItemService.findAllByOrder_Id(itemDTO.getOrderId());
        Order order = findById(itemDTO.getOrderId());
        orderItems.add(orderItemService.convertOrderItemDTOToOrderItem(itemDTO));
        order.setOrderItems(orderItems);
//        order.setTotalPrice(calcTotalPrice(order));
        return order;
    }


//    public Order getOrder(OrderItemDTO itemDTO, Long clientId) {
//        Order order = null;
//        if (itemDTO.getOrderId() == null) {
//            return createOrder(itemDTO, clientId);
//        }
//        order = findOrderByIdAndAndClientId(itemDTO.getOrderId(), clientId);
//        if (order == null) {
//            return createOrder(itemDTO, clientId);
//        } else {
//            System.out.println(order.getOrderItems());
//            return order;
//        }
//
//    }



    public Order createOrder(OrderItemDTO itemDTO, Long clientId) {
        OrderItem orderItem = orderItemService.convertOrderItemDTOToOrderItem(itemDTO);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        return save(Order.builder()
                .client(clientService.findById(clientId))
                .orderStatus(OrderStatus.UNPAYED)
                .totalPrice(productService
                        .findById(itemDTO.getProductId())
                        .getPrice().multiply(new BigDecimal(itemDTO.getCount())))
                .orderItems(items)
                .build());
    }



//    public Order createOrder(OrderItemDTO itemDTO, Long clientId) {
//        return save(Order.builder()
//                .client(clientService.findById(clientId))
//                .orderStatus(OrderStatus.UNPAYED)
//                .totalPrice(productService
//                        .findById(itemDTO.getProductId())
//                        .getPrice().multiply(new BigDecimal(itemDTO.getCount())))
//                .build());
//    }

    public Order chekOrder(OrderItemDTO itemDTO, Long clientId) {
        Order order = findOrderByIdAndAndClientId(itemDTO.getOrderId(), clientId);
        if (itemDTO.getOrderId() == null || order == null) {
            return save(Order.builder()
                    .client(clientService.findById(clientId))
                    .orderStatus(OrderStatus.UNPAYED)
                    .totalPrice(productService
                            .findById(itemDTO.getProductId())
                            .getPrice().multiply(new BigDecimal(itemDTO.getCount())))
                    .build());
        } else return order;

    }

    public OrderDTO convertOrderToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public boolean chek(OrderItemDTO itemDTO, Long clientId) {
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

//    public Order transferOrderDTOToOrder(OrderDTO orderDTO) {
//        List<OrderItemDTO> itemDTOList = orderDTO.getItemDTOList();
//        List<OrderItem> items = new ArrayList<>();
//        itemDTOList.forEach(o -> {
//            OrderItem item = OrderItem.builder()
//                    .id(o.getId())
//                    .count(o.getCount())
//                    .order(findById(orderDTO.getId()))
//                    .product(productRepository.findById(o.getProductId()))
//                    .build();
//            items.add(item);
//        });
//    }


//    public Order getOrderFromRequest(JsonNode node, ObjectMapper mapper) {
//        JsonNode orderNode = node.get("order");
//        Order order = null;
//        try {
//            order = mapper.treeToValue(orderNode, Order.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return order;
//    }
}
