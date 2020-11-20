package ru.sber.internship.rabbitmq.mqcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.OrderItemDTO;

@RestController
@RequestMapping(value = "/mq/items", produces = "application/json")
public class OrderItemControllerMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public String findAll() {
        rabbitTemplate.convertAndSend("request", "orderItems.all", "all");
        return "Message sent successfully to the queue.";
    }

    @GetMapping("/{clientId}/all")
    public String findAllByClientId(@PathVariable("clientId") Long clientId) {
        rabbitTemplate.convertAndSend("request", "orderItems.all.clientId", clientId);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("{itemId}/client")
    public String findClientByItemId(@PathVariable("itemId") Long id) {
        rabbitTemplate.convertAndSend("request", "orderItems.client.itemId", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("{itemId}/order")
    public String findOrderByItemId(@PathVariable("itemId") Long id) {
        rabbitTemplate.convertAndSend("request", "orderItems.order.itemId", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable(value = "id") long id) {
        rabbitTemplate.convertAndSend("request", "orderItems.id", id);
        return "Message sent successfully to the queue.";
    }

    @GetMapping("{itemId}/product")
    public String showProductByItemId(@PathVariable("itemId") Long id) {
        rabbitTemplate.convertAndSend("request", "orderItems.product.itemId", id);
        return "Message sent successfully to the queue.";
    }

    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    public String add(@RequestBody OrderItem item) {
        try {
            String msg = mapper.writeValueAsString(item);
            rabbitTemplate.convertAndSend("request", "orderItems.add", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";
    }


    @PostMapping(value = "/{clientId}/add", consumes = {"application/json", "application/xml"})
    @Transactional
    public String addItemToClient(@RequestBody OrderItemDTO itemDTO, @PathVariable("clientId") Long clientId) {
        try {
            String msg = mapper.writeValueAsString(itemDTO);
            msg = clientId + "/" + msg;
            rabbitTemplate.convertAndSend("request", "orderItems.add.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    public String update(@RequestBody OrderItem item) {
        try {
            String msg = mapper.writeValueAsString(item);
            rabbitTemplate.convertAndSend("request", "orderItems.update", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";

    }


    @PutMapping(value = "/{clientId}/update", consumes = {"application/json", "application/xml"})
    @Transactional
    public String updateClientItem(@RequestBody OrderItemDTO itemDTO,
                                   @PathVariable("clientId") Long clientId) {
        try {
            String msg = mapper.writeValueAsString(itemDTO);
            msg = clientId + "/" + msg;
            rabbitTemplate.convertAndSend("request", "orderItems.update.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message sent successfully to the queue.";
    }


    @DeleteMapping("/{clientId}/{id}")
    public String deleteByClient(@PathVariable(value = "id") long id,
                                 @PathVariable(value = "clientId") long clientId) {
        String msg = clientId + "/" + id;
        rabbitTemplate.convertAndSend("request", "orderItems.delete.clientId", msg);
        return "Message sent successfully to the queue.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") long id) {
        rabbitTemplate.convertAndSend("request", "orderItems.delete", id);
        return "Message sent successfully to the queue.";
    }

}
