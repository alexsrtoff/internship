package ru.sber.internship.rabbitmq.mqlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.sber.internship.controller.ClientController;
import ru.sber.internship.entity.dto.ClientDTO;
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


//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "request", durable = "true"),
//            exchange = @Exchange(
//                    value = "request",
//                    type = ExchangeTypes.DIRECT),
//            key = {"clients.all"}))
//    public void findAll(String msg) {
////        if (key.equals("clients.all")) {
//            List<ClientDTO> list = clientService.convertClientListToClientDTOList(clientService.findAll());
//            String response = null;
//            try {
//                if (msg.equals("json")) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    response = mapper.writeValueAsString(list);
//                } else {
//                    XmlMapper xmlMapper = new XmlMapper();
//                    response = xmlMapper.writeValueAsString(list);
//
//                }
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            rabbitTemplate.convertAndSend("response", "clients.all", response);
////        }
//    }



//    @RabbitListener(queues = "request")
//    public void findAll(String msg, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
//        if (key.equals("clients.all")) {
//            List<ClientDTO> list = clientService.convertClientListToClientDTOList(clientService.findAll());
//            String response = null;
//            try {
//                if (msg.equals("json")) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    response = mapper.writeValueAsString(list);
//                } else {
//                    XmlMapper xmlMapper = new XmlMapper();
//                    response = xmlMapper.writeValueAsString(list);
//
//                }
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            rabbitTemplate.convertAndSend("response", "clients.all", response);
//        }
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "request", durable = "true"),
            exchange = @Exchange(
                    value = "request",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT),
            key = {"clients.add"}))
    public void add(String msg, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        if (key.equals("clients.add")) {
            String[] tokens = msg.split("/",2);
            System.out.println(tokens[0]);
            System.out.println(tokens[1]);


            List<ClientDTO> list = clientService.convertClientListToClientDTOList(clientService.findAll());
            String response = null;
            try {
                if (tokens[0].equals("json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    ClientDTO clientDTO = mapper.readValue(tokens[1], ClientDTO.class);
                    ClientDTO add = clientController.add(clientDTO);
                    System.out.println(add);
                    response = mapper.writeValueAsString(add);
                } else {
                    XmlMapper xmlMapper = new XmlMapper();
                    response = xmlMapper.writeValueAsString(list);

                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            rabbitTemplate.convertAndSend("response", "clients.all", response);
        }
    }

}
