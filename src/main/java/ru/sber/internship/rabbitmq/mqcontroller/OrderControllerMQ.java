package ru.sber.internship.rabbitmq.mqcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;
import ru.sber.internship.entity.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/mq/orders", produces = "application/json")
public class OrderControllerMQ {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public String findAll() {
        rabbitTemplate.convertAndSend("request", "orders.all", "all");
        return "Message sent successfully to the queue.";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable(value = "id") int id) {
        rabbitTemplate.convertAndSend("request", "orders.id", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("{odredId}/products")
    public String showProductsByOrderId(@PathVariable("odredId") Long id) {
        rabbitTemplate.convertAndSend("request", "orders.products.orderId", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("{odredId}/client")
    public String showClientByOrderId(@PathVariable("odredId") Long id) {
        rabbitTemplate.convertAndSend("request", "orders.client.orderId", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("/{orderId}/items")
    public String showOrderItemsByOrderId(@PathVariable("orderId") Long id) {
        rabbitTemplate.convertAndSend("request", "orders.items.orderId", id);
        return "Message sent successfully to the queue.";
    }


    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    public String add(@RequestBody OrderDTO orderDTO) {
        try {
            String msg = mapper.writeValueAsString(orderDTO);
            rabbitTemplate.convertAndSend("request", "orders.add", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    public String update(@RequestBody OrderDTO orderDTO) {
        try {
            String msg = mapper.writeValueAsString(orderDTO);
            rabbitTemplate.convertAndSend("request", "orders.update", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") long id) {
        rabbitTemplate.convertAndSend("request", "orders.delete", id);
        return "Message sent successfully to the queue.";
    }


    @DeleteMapping("/{clientId}/{id}")
    public String deleteByClient(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "clientId") long clientId) {
        String msg = id + "/" + clientId;
        rabbitTemplate.convertAndSend("request", "orders.delete.clientId", msg);
        return "Message sent successfully to the queue.";
    }

}
