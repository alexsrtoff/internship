package ru.sber.internship.rabbitmq.mqlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.internship.controller.ClientController;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;

import java.util.List;

@EnableRabbit
@Component
public class ClientListener {

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ClientController clientController;

    @RabbitListener(queues = "request.clients.all")
    public void findAll(String msg) {
        List<ClientDTO> list = clientService.convertClientListToClientDTOList(clientService.findAll());
        String response = null;
        try {
            if (msg.equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(list);
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                response = xmlMapper.writeValueAsString(list);

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.all", response);
    }

    @RabbitListener(queues = "request.clients.add")
    public void add(String msg) {
        String[] tokens = msg.split("/", 2);
        String response = null;
        try {
            if (tokens[0].equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                ClientDTO clientDTO = mapper.readValue(tokens[1], ClientDTO.class);
                ClientDTO newClient = clientController.add(clientDTO);
                response = mapper.writeValueAsString(newClient);
            } else {
                XmlMapper mapper = new XmlMapper();
                ClientDTO clientDTO = mapper.readValue(tokens[1], ClientDTO.class);
                ClientDTO newClient = clientController.add(clientDTO);
                response = mapper.writeValueAsString(newClient);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.add", response);
    }

    @RabbitListener(queues = "request.clients.showById")
    public void findById(String msg) {
        String[] tokens = msg.split("/", 2);
        String response = null;
        try {
            ClientDTO client = clientController.findClientById(Integer.parseInt(tokens[1]));
            if (tokens[0].equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(client);
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                response = xmlMapper.writeValueAsString(client);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.showById", response);
    }


    @RabbitListener(queues = "request.clients.showOrdersById")
    public void showOrdersByClientId(String msg) {
        String[] tokens = msg.split("/", 2);
        String type = tokens[0];
        long clientId = Long.parseLong(tokens[1]);
        String response = null;
        try {
            List<OrderDTO> orders = orderService.convertOrderListToOrderDTOList(orderService.findAllByClientId(clientId));
            if (type.equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(orders);
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                response = xmlMapper.writeValueAsString(orders);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.showOrdersById", response);
    }


    @RabbitListener(queues = "request.clients.update")
    public void update(String msg) {
        String[] tokens = msg.split("/", 2);
        String response = null;
        try {
            if (tokens[0].equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                ClientDTO clientDTO = mapper.readValue(tokens[1], ClientDTO.class);
                ClientDTO newClient = clientController.update(clientDTO);
                response = mapper.writeValueAsString(newClient);
            } else {
                XmlMapper mapper = new XmlMapper();
                ClientDTO clientDTO = mapper.readValue(tokens[1], ClientDTO.class);
                ClientDTO newClient = clientController.update(clientDTO);
                response = mapper.writeValueAsString(newClient);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.update", response);
    }

    @RabbitListener(queues = "request.clients.delete")
    public void delete(String msg) {
        String[] tokens = msg.split("/", 2);
        String type = tokens[0];
        long clientId = Long.parseLong(tokens[1]);
        String response = null;
        try {
            boolean delete = clientService.deleteById(clientId);
            if (type.equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(delete);
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                response = xmlMapper.writeValueAsString(delete);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("response", "clients.delete", response);
    }

}
