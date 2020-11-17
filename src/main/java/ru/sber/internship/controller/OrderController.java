package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.entity.utils.OrderStatus;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = {"application/json", "application/xml"})
public class OrderController {


    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    ProductServiceImpl productService;


    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.convertOrderListToOrderDTOList(orderService.findAll());
    }


    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable(value = "id") int id) {
        if (orderService.findById(id) != null) {
            return orderService.convertOrderToOrderDTO(orderService.findById(id));
        } else return new OrderDTO();
    }

    @GetMapping("{odredId}/products")
    public List<ProductDTO> showProductsByOrderId(@PathVariable("odredId") Long id) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        Order order = orderService.findById(id);
        if (order != null) {
            productDTOList = productService.createProductDTOList(order.getOrderItems());
        }
        return productDTOList;
    }

    @GetMapping("{odredId}/client")
    public ClientDTO showClientByOrderId(@PathVariable("odredId") Long id) {
        Order order = orderService.findById(id);
        if (order != null) {
            return clientService.convertClientToClientDTO(order.getClient());
        }
        return new ClientDTO();
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemDTO> showOrderItemsByOrderId(@PathVariable("orderId") Long id) {
        List<OrderItemDTO> itemDTOS = new ArrayList<>();
        Order order = orderService.findById(id);
        if (order != null) {
            itemDTOS = orderItemService.convertListOrderItemToListOrderItemDTO(order.getOrderItems());
        }
        return itemDTOS;
    }


    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    @Transactional
    public OrderDTO add(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.findById(orderDTO.getId());
        if (order != null) {
            return update(orderDTO);
        }
        order = orderService.convertOrderDTOToOrder(orderDTO);
        return orderService.convertOrderToOrderDTO(orderService.save(order));
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    @Transactional
    public OrderDTO update(@RequestBody OrderDTO orderDTO) {
        boolean chekOrder = orderService.chekOrder(orderDTO);
        if (chekOrder) {
            orderDTO = orderService.update(orderDTO);
        } else {
            orderDTO.setOrderStatus(OrderStatus.UNPAYED);
            orderDTO = add(orderDTO);
        }
        return orderDTO;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") long id) {
        return orderService.deleteById(id);
    }


    @DeleteMapping("/{clientId}/{id}")
    public boolean deleteByClient(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "clientId") long clientId) {
        return orderService.deleteByIdAndClientId(id, clientId);
    }
}
