package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderItemServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;
import ru.sber.internship.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
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
    public List<OrderItemDTO> showOrderItemsByOrderId(@PathVariable("orderId") Long id){
        List<OrderItemDTO> itemDTOS = new ArrayList<>();
        Order order = orderService.findById(id);
        if (order != null) {
            itemDTOS = orderItemService.convertListOrderItemToListOrderItemDTO(order.getOrderItems());
        }
        return itemDTOS;
    }


    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public OrderDTO add(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getId() != null) orderDTO.setId(null);
        Order order = orderService.convertOrderDTOToOrder(orderDTO);
        return orderService.convertOrderToOrderDTO(orderService.save(order));
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public OrderDTO update(@RequestBody OrderDTO orderDTO) {
        if (orderService.chekOrder(orderDTO)) {
            Order order = orderService.findById(orderDTO.getId());
            order.setOrderStatus(orderDTO.getOrderStatus());
            return orderService.convertOrderToOrderDTO(orderService.save(order));
        }
        return new OrderDTO();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") long id) {
        return orderService.deleteById(id);
    }


    @DeleteMapping("/{clientId}/{id}")
    public boolean deleteByClient(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "clientId") long clientId) {
        return orderService.deleteByIdAndClient_Id(id, clientId);
    }




}
