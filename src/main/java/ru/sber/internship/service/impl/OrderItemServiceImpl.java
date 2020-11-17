package ru.sber.internship.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.repository.OrderItemRepository;
import ru.sber.internship.service.OrderItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    OrderServiceImpl orderService;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(long id) {
        return orderItemRepository.findById(id);
    }

    public boolean deleteById(long id, long clienId) {
        if (orderItemRepository.findOrderItemByIdAndOrderClientId(id, clienId) == null) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;
    }

    public boolean delete(long id) {
        if (orderItemRepository.findById(id) == null) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;

    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAllByOrderClientId(Long clientId) {
        return orderItemRepository.findAllByOrder_Client_Id(clientId);
    }

    @Override
    public List<OrderItem> findAllByOrder_Id(long orderId) {
        return orderItemRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public List<OrderItem> findAllByProductId(long productId) {
        return orderItemRepository.findAllByProductId(productId);
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


    public OrderItem convertOrderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        return save(OrderItem.builder()
                .id(orderItemDTO.getId())
                .count(orderItemDTO.getCount())
                .product(productService.findById(orderItemDTO.getProductId()))
                .order(orderService.findById(orderItemDTO.getOrderId()))
                .build());
    }


    public OrderItemDTO convertOrderItemToOrderItemDTO(OrderItem o) {
        return OrderItemDTO.builder()
                .id(o.getId())
                .count(o.getCount())
                .orderId(o.getOrder().getId())
                .productId(o.getProduct().getId())
                .build();
    }

    public List<OrderItemDTO> convertListOrderItemToListOrderItemDTO(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(o -> convertOrderItemToOrderItemDTO(o))
                .collect(Collectors.toList());
    }
}
