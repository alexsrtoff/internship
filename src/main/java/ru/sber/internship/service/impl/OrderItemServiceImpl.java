package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.repository.OrderItemRepository;
import ru.sber.internship.service.OrderItemService;

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

    /**
     * finds all OrderItems
     *
     * @return
     */
    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    /**
     * finds an OrderItem by Id
     *
     * @param id
     * @return
     */
    public OrderItem findById(long id) {
        return orderItemRepository.findById(id);
    }

    /**
     * deletes an OrderItem by OrderItemId and ClientId
     *
     * @param id
     * @param clienId
     * @return
     */
    public boolean deleteById(long id, long clienId) {
        if (findOrderItemByIdAndOrderClientId(id, clienId) == null) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;
    }

    /**
     * deletes an OrderItem by OrderItemId
     *
     * @param id
     * @return
     */
    public boolean delete(long id) {
        if (orderItemRepository.findById(id) == null) {
            return false;
        }
        orderItemRepository.deleteById(id);
        return true;

    }

    /**
     * Saves OrderItem
     *
     * @param orderItem
     * @return
     */
    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * finds all OrdderItems by ClientId
     *
     * @param clientId
     * @return
     */
    @Override
    public List<OrderItem> findAllByOrderClientId(Long clientId) {
        return orderItemRepository.findAllByOrderClientId(clientId);
    }

    /**
     * finds all OrdderItems by OrderId
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> findAllByOrderId(long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    /**
     * finds all OrdderItems by ProductId
     *
     * @param productId
     * @return
     */
    @Override
    public List<OrderItem> findAllByProductId(long productId) {
        return orderItemRepository.findAllByProductId(productId);
    }

    /**
     * finds all OrdderItems by OrderItemId and ClientId
     *
     * @param itemId
     * @param clientId
     * @return
     */
    @Override
    public OrderItem findOrderItemByIdAndOrderClientId(Long itemId, Long clientId) {
        return orderItemRepository.findOrderItemByIdAndOrderClientId(itemId, clientId);
    }

    /**
     * converts OrderItemDTO to OrderItem and save it
     *
     * @param orderItemDTO
     * @return
     */
    public OrderItem convertOrderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        return save(OrderItem.builder()
                .id(orderItemDTO.getId())
                .count(orderItemDTO.getCount())
                .product(productService.findById(orderItemDTO.getProductId()))
                .order(orderService.findById(orderItemDTO.getOrderId()))
                .build());
    }

    /**
     * converts OrderItem to OrderItemDTO
     *
     * @param o
     * @return
     */
    public OrderItemDTO convertOrderItemToOrderItemDTO(OrderItem o) {
        return OrderItemDTO.builder()
                .id(o.getId())
                .count(o.getCount())
                .orderId(o.getOrder().getId())
                .productId(o.getProduct().getId())
                .build();
    }

    /**
     * converts list of OrderItems to list of OrderItemDTOs
     *
     * @param orderItems
     * @return
     */
    public List<OrderItemDTO> convertListOrderItemToListOrderItemDTO(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(o -> convertOrderItemToOrderItemDTO(o))
                .collect(Collectors.toList());
    }
}
