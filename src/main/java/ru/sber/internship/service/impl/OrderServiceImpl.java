package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

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

    @Override
    public BigDecimal calcTotalPrice(Order order) {
       return order.getOrderItems().stream().
                map(p -> p.getProduct().getPrice().multiply(new BigDecimal(p.getCount()))).
                reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public boolean deleteById(long id){
        if (orderRepository.findById(id) == null){
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> findAllByClient(Client client) {
        return orderRepository.findAllByClient(client);
    }


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
