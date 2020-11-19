package ru.sber.internship.rabbitmq.mqcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.dto.ClientDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/mq/clients", produces = {"application/json", "application/xml"})
public class ClientControllerMQ {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/all")
    public String findAll(@RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();

        if (mediaType.equals("json")) {
            rabbitTemplate.convertAndSend("request", "clients.all", "json");
            return "Message sent successfully to the queue.";
        } else if (mediaType.equals("xml")) {
            rabbitTemplate.convertAndSend("request", "clients.all", "xml");
            return "Message sent successfully to the queue.";
        } else {
            return "Message was not sent to the queue. Wrong content type";
        }
    }


    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    @Transactional
    public String add(@RequestBody ClientDTO clientDTO, @RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();
        try {
            if (mediaType.equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                String clientDTOToString = mapper.writeValueAsString(clientDTO);
                String msg = "json/" + clientDTOToString;
                rabbitTemplate.convertAndSend("request", "clients.add", msg);
                return "Message sent successfully to the queue.";
            } else if (mediaType.equals("xml")) {
                rabbitTemplate.convertAndSend("request", "clients.add", "xml");
                return "Message sent successfully to the queue.";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message was not sent to the queue. Wrong content type";

//        Client client = clientService.findById(clientDTO.getId());
//        if (client != null) {
//            return clientService.convertClientToClientDTO(client);
//        }
//        Client newClient = clientService.convertClientDTOToClient(clientDTO);
//        return clientService.convertClientToClientDTO(newClient);

    }


}
