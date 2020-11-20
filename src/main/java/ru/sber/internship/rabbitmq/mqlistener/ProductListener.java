package ru.sber.internship.rabbitmq.mqlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.internship.controller.ProductController;
import ru.sber.internship.entity.dto.ProductDTO;

import java.util.List;

@EnableRabbit
@Component
public class ProductListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ProductController productController;

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "request.products.all")
    public void findAll() {
        ObjectMapper mapper = new ObjectMapper();
        List<ProductDTO> productDTOList = productController.findAll();
        String value = null;
        try {
            value = mapper.writeValueAsString(productDTOList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "products.all", value);
    }

    @RabbitListener(queues = "request.products.id")
    public void findById(Integer id) {
        ProductDTO productDTO = productController.findById(id);
        String value = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            value = mapper.writeValueAsString(productDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "products.id", value);
    }

    @RabbitListener(queues = "request.products.add")
    public void add(String msg) {
        String value = null;
        try {
            ProductDTO productDTO = mapper.readValue(msg, ProductDTO.class);
            ProductDTO newProductDTO = productController.add(productDTO);
            value = mapper.writeValueAsString(newProductDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "products.add", value);
    }

    @RabbitListener(queues = "request.products.update")
    public void update(String msg) {
        try {
            ProductDTO productDTO = mapper.readValue(msg, ProductDTO.class);
            ProductDTO updateProductDTO = productController.update(productDTO);
            msg = mapper.writeValueAsString(updateProductDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "products.update", msg);
    }

    @RabbitListener(queues = "request.products.delete")
    public void delete(Long id) {
        String msg = null;
        boolean delete = productController.delete(id);
        try {
            msg = mapper.writeValueAsString(delete);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "products.delete", msg);
    }


}
