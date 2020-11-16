package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/items", produces = {"application/json", "application/xml"})
public class OrderItemController {

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    OrderServiceImpl orderService;


    @GetMapping
    public List<OrderItemDTO> findAll() {
        return orderItemService.convertListOrderItemToListOrderItemDTO(orderItemService.findAll());
    }

    @GetMapping("/{clientId}/all")
    public List<OrderItemDTO> findAllByClientId(@PathVariable("clientId") Long clientId) {
        return orderItemService.convertListOrderItemToListOrderItemDTO(orderItemService.findAllByOrderClientId(clientId));
    }

    @GetMapping("{itemId}/client")
    public ClientDTO findClientByItemId(@PathVariable("itemId") Long id) {
        OrderItem item = orderItemService.findById(id);
        if (item == null) {
            return new ClientDTO();
        }
        return clientService.convertClientToClientDTO(item.getOrder().getClient());
    }

    @GetMapping("{itemId}/order")
    public OrderDTO findOrderByItemId(@PathVariable("itemId") Long id) {
        OrderItem item = orderItemService.findById(id);
        if (item == null) {
            return new OrderDTO();
        }
        return orderService.convertOrderToOrderDTO(item.getOrder());
    }

    @GetMapping("/{id}")
    public OrderItemDTO findById(@PathVariable(value = "id") long id) {
        OrderItem item = orderItemService.findById(id);

        if (item != null) {
            return orderItemService.convertOrderItemToOrderItemDTO(item);
        } else return new OrderItemDTO();
    }

    @GetMapping("{itemId}/product")
    public ProductDTO showProductByItemId(@PathVariable("itemId") Long id) {
        OrderItem item = orderItemService.findById(id);
        if (item != null) {
            return productService.convertProductToProductDTO(item.getProduct());
        } else return new ProductDTO();
    }

    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    public OrderItem add(@RequestBody OrderItem item) {
        return orderItemService.save(item);
    }


    @PostMapping(value = "/{clientId}/add", consumes = {"application/json", "application/xml"})
    @Transactional
    public OrderItemDTO addItemToClient(@RequestBody OrderItemDTO itemDTO, @PathVariable("clientId") Long clientId) {
        if (orderService.chekOrderByOrderItem(itemDTO, clientId)) {
            OrderItem orderItem = orderItemService.convertOrderItemDTOToOrderItem(itemDTO);
            orderService.calcTotalPrice(itemDTO.getOrderId());
            itemDTO.setId(orderItem.getId());
            itemDTO.setProductId(orderItem.getProduct().getId());
            return itemDTO;
        } else {
            Order order = orderService.createOrder(itemDTO, clientId);
            return orderItemService.convertOrderItemToOrderItemDTO(order.getOrderItems().get(0));
        }
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    public OrderItem update(@RequestBody OrderItem itemDTO) {
        if (itemDTO.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return orderItemService.save(itemDTO);
    }


    @PutMapping(value = "/{clientId}/update", consumes = {"application/json", "application/xml"})
    @Transactional
    public OrderItemDTO updateClientItem(@RequestBody OrderItemDTO itemDTO, @PathVariable("clientId") Long clientId) {
        if (itemDTO.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        if (orderService.chekOrderByOrderItem(itemDTO, clientId)) {
            orderItemService.convertOrderItemDTOToOrderItem(itemDTO);
            orderService.calcTotalPrice(itemDTO.getOrderId());
            return itemDTO;
        } else {
            return new OrderItemDTO();
        }
    }


    @DeleteMapping("/{clientId}/{id}")
    public boolean deleteByClient(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "clientId") long clientId) {
        return orderItemService.deleteById(id, clientId);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") long id) {
        return orderItemService.delete(id);
    }
}
