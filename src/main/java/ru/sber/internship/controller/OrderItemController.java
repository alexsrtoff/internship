package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/items")
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
    public OrderItem findById(@PathVariable(value = "id") long id) {
        if (orderItemService.findById(id) != null) {
            return orderItemService.findById(id);
        } else return new OrderItem();
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public OrderItem add(@RequestBody OrderItem item) {
        return orderItemService.save(item);
    }


    @PostMapping(value = "/{clientId}/add", consumes = "application/json", produces = "application/json")
    @Transactional
    public OrderItemDTO addItemToClient(@RequestBody OrderItemDTO itemDTO, @PathVariable("clientId") Long clientId) {
        if (orderService.chek(itemDTO, clientId)) {
            OrderItem orderItem = orderItemService.convertOrderItemDTOToOrderItem(itemDTO);
            orderService.calcTotalPrice(itemDTO.getOrderId());
            itemDTO.setId(orderItem.getId());
            itemDTO.setProduct(productService.convertProductToProductDTO(productService.findById(itemDTO.getProductId())));
            return itemDTO;
        } else {
            return new OrderItemDTO();
        }


    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public OrderItem update(@RequestBody OrderItem itemDTO) {

        if (itemDTO.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        return orderItemService.save(itemDTO);
    }


    @PutMapping(value = "/{clientId}/update", consumes = "application/json", produces = "application/json")
    public OrderItemDTO updateClientItem(@RequestBody OrderItemDTO itemDTO, @PathVariable("clientId") Long clientId) {
        if (itemDTO.getId() == null) {
            throw new IllegalArgumentException("Id not found in the update request");
        }
        if (orderService.chek(itemDTO, clientId)) {
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
