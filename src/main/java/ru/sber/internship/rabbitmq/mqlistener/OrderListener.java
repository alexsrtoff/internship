package ru.sber.internship.rabbitmq.mqlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.internship.controller.OrderController;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;

import java.util.List;

@Component
@EnableRabbit
public class OrderListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderController orderController;

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "request.orders.all")
    public void findAll() {
        List<OrderDTO> orderDTOS = orderController.findAll();
        try {
            String msg = mapper.writeValueAsString(orderDTOS);
            rabbitTemplate.convertAndSend("response", "orders.all", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.id")
    public void findById(Long id) {
        OrderDTO orderDTO = orderController.findById(id);
        try {
            String msg = mapper.writeValueAsString(orderDTO);
            rabbitTemplate.convertAndSend("response", "orders.id", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.products.orderId")
    public void findProductsByOrderId(Long id) {
        List<ProductDTO> productDTOS = orderController.showProductsByOrderId(id);
        try {
            String msg = mapper.writeValueAsString(productDTOS);
            rabbitTemplate.convertAndSend("response", "orders.products.orderId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.client.orderId")
    public void findClientByOrdrId(Long id) {
        ClientDTO clientDTO = orderController.showClientByOrderId(id);
        try {
            String msg = mapper.writeValueAsString(clientDTO);
            rabbitTemplate.convertAndSend("response", "orders.client.orderId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.items.orderId")
    public void findItemsByOrderId(Long id) {
        List<OrderItemDTO> itemDTOS = orderController.showOrderItemsByOrderId(id);
        try {
            String msg = mapper.writeValueAsString(itemDTOS);
            rabbitTemplate.convertAndSend("response", "orders.items.orderId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.add")
    public void add(String msg) {
        try {
            OrderDTO orderDTO = mapper.readValue(msg, OrderDTO.class);
            OrderDTO newOrderDTO = orderController.add(orderDTO);
            msg = mapper.writeValueAsString(newOrderDTO);
            rabbitTemplate.convertAndSend("response", "orders.add", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.update")
    public void update(String msg){
        try {
            OrderDTO orderDTO = mapper.readValue(msg, OrderDTO.class);
            OrderDTO updateOrderDTO = orderController.update(orderDTO);
            msg = mapper.writeValueAsString(updateOrderDTO);
            rabbitTemplate.convertAndSend("response", "orders.update", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "request.orders.delete")
    public void delete(Long id){
        boolean delete = orderController.delete(id);
        try {
            String msg = mapper.writeValueAsString(delete);
            rabbitTemplate.convertAndSend("response","orders.delete", msg );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orders.delete.clientId")
    public void deletOrderByIdAndClientId(String msg){
        String[] tokens = msg.split("/");
        Long id = Long.valueOf(tokens[0]);
        Long clientId = Long.valueOf(tokens[1]);
        boolean deleteByClient = orderController.deleteByClient(id, clientId);
        try {
            msg = mapper.writeValueAsString(deleteByClient);
            rabbitTemplate.convertAndSend("response", "orders.delete.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
