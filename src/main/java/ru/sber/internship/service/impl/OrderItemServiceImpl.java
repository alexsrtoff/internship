package ru.sber.internship.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.repository.OrderItemRepository;
import ru.sber.internship.repository.ProductRepository;
import ru.sber.internship.service.OrderItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(long id) {
        return orderItemRepository.findById(id);
    }

    public boolean deleteById(long id) {
        if (orderItemRepository.findById(id) == null) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;
    }

    @Override
    public List<OrderItem> findAllByOrder(Order order) {
        return findAllByOrder(order);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItemDTO> getOrderItemsDTOFromRequest(JsonNode node, ObjectMapper mapper) {
        JsonNode orderItemsNone = node.get("orderItemsDTO");
        List<OrderItemDTO> orderItemList = new ArrayList<>();
        try {
            OrderItemDTO[] orderItemsArray = mapper.treeToValue(orderItemsNone, OrderItemDTO[].class);
            orderItemList = Arrays.asList(orderItemsArray);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orderItemList;
    }

    public List<OrderItem> transferOrderItemsDTOToOrderItems(JsonNode node, ObjectMapper mapper, Order order) {
        List<OrderItemDTO> orderItemsDTOFromRequest = getOrderItemsDTOFromRequest(node, mapper);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItemsDTOFromRequest.forEach(o -> {
            OrderItem orderItem = null;
            if (o.getId() == null) {
                orderItem = OrderItem.builder()
                        .id(o.getId())
                        .count(o.getCount())
                        .product(productRepository.findById(o.getProductId()))
                        .order(order)
                        .build();
            } else {
                OrderItem item = findById(o.getId());
                item.setCount(o.getCount());
                item.setProduct(productRepository.findById(o.getProductId()));
                item.setOrder(order);
            }
            orderItems.add(orderItem);
        });
        return orderItems;
    }
}
