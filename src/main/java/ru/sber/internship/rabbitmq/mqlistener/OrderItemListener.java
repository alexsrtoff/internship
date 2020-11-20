package ru.sber.internship.rabbitmq.mqlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.internship.controller.OrderItemController;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.dto.ProductDTO;

import java.util.List;

@EnableRabbit
@Component
public class OrderItemListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderItemController orderItemController;

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "request.orderItems.all")
    public void findAll() {
        List<OrderItemDTO> itemDTOS = orderItemController.findAll();
        try {
            String msg = mapper.writeValueAsString(itemDTOS);
            rabbitTemplate.convertAndSend("response", "orderItems.all", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.all.clientId")
    public void findAllByClientID(Long id) {
        List<OrderItemDTO> allByClientId = orderItemController.findAllByClientId(id);
        try {
            String msg = mapper.writeValueAsString(allByClientId);
            rabbitTemplate.convertAndSend("response", "orderItems.all.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.client.itemId")
    public void findClientByItemId(Long id) {
        ClientDTO clientByItemId = orderItemController.findClientByItemId(id);
        try {
            String msg = mapper.writeValueAsString(clientByItemId);
            rabbitTemplate.convertAndSend("response", "orderItems.client.itemId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.order.itemId")
    public void findOrderByItemId(Long id) {
        OrderDTO orderByItemId = orderItemController.findOrderByItemId(id);
        try {
            String msg = mapper.writeValueAsString(orderByItemId);
            rabbitTemplate.convertAndSend("response", "orderItems.order.itemId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.id")
    public void findById(Long id) {
        OrderItemDTO ordetItembyId = orderItemController.findById(id);
        try {
            String msg = mapper.writeValueAsString(ordetItembyId);
            rabbitTemplate.convertAndSend("response", "orderItems.id", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.product.itemId")
    public void findProductByItemId(Long id) {
        ProductDTO productDTO = orderItemController.showProductByItemId(id);
        try {
            String msg = mapper.writeValueAsString(productDTO);
            rabbitTemplate.convertAndSend("response", "orderItems.product.itemId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.add")
    public void add(String msg) {
        try {
            OrderItem orderItem = mapper.readValue(msg, OrderItem.class);
            OrderItem newOrderItem = orderItemController.add(orderItem);
            msg = mapper.writeValueAsString(newOrderItem);
            rabbitTemplate.convertAndSend("response", "orderItems.add", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.add.clientId")
    public void addItemToClient(String msg) {
        String[] tokens = msg.split("/", 2);
        Long clientId = Long.valueOf(tokens[0]);
        try {
            OrderItemDTO orderItemDTO = mapper.readValue(tokens[1], OrderItemDTO.class);
            OrderItemDTO newOrderItemDTO = orderItemController.addItemToClient(orderItemDTO, clientId);
            msg = mapper.writeValueAsString(newOrderItemDTO);
            rabbitTemplate.convertAndSend("response", "orderItems.add.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.update")
    public void update(String msg) {
        try {
            OrderItem orderItem = mapper.readValue(msg, OrderItem.class);
            OrderItem newOrderItem = orderItemController.update(orderItem);
            msg = mapper.writeValueAsString(newOrderItem);
            rabbitTemplate.convertAndSend("response", "orderItems.update", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.update.clientId")
    public void updateByClientId(String msg) {
        String[] tokens = msg.split("/", 2);
        Long clientId = Long.valueOf(tokens[0]);
        try {
            OrderItemDTO orderItemDTO = mapper.readValue(tokens[1], OrderItemDTO.class);
            OrderItemDTO newOrderItemDTO = orderItemController.addItemToClient(orderItemDTO, clientId);
            msg = mapper.writeValueAsString(newOrderItemDTO);
            rabbitTemplate.convertAndSend("response", "orderItems.update.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.delete")
    public void delete(Long id) {
        boolean delete = orderItemController.delete(id);
        try {
            String msg = mapper.writeValueAsString(delete);
            rabbitTemplate.convertAndSend("response", "orderItems.delete", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "request.orderItems.delete.clientId")
    public void deleteByClientId(String msg) {
        String[] tokens = msg.split("/", 2);
        long clientId = Long.parseLong(tokens[0]);
        long id = Long.parseLong(tokens[1]);
        boolean delete = orderItemController.deleteByClient(id, clientId);
        try {
            msg = mapper.writeValueAsString(delete);
            rabbitTemplate.convertAndSend("response", "orderItems.delete.clientId", msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
