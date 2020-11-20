package ru.sber.internship.rabbitmq.mqcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.dto.ProductDTO;


@RestController
@RequestMapping(value = "/mq/products", produces = "application/json")
public class ProductControllerMQ {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public String findAll() {
        rabbitTemplate.convertAndSend("request", "products.all", "all");
        return "Message sent successfully to the queue.";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable(value = "id") int id) {
        rabbitTemplate.convertAndSend("request", "products.id", id);
        return "Message sent successfully to the queue.";
    }


    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    public String add(@RequestBody ProductDTO productDTO) {
        String msg = null;
        try {
            msg = mapper.writeValueAsString(productDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("request", "products.add", msg);
        return "Message sent successfully to the queue.";
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    public String update(@RequestBody ProductDTO productDTO) {
        String msg = null;
        try {
            msg = mapper.writeValueAsString(productDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("request", "products.update", msg);
        return "Message sent successfully to the queue.";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") long id) {
        rabbitTemplate.convertAndSend("request", "products.delete", id);
        return "Message sent successfully to the queue.";
    }
}
