package ru.sber.internship.rabbitmq.mqcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.dto.ClientDTO;

@RestController
@RequestMapping(value = "/mq/clients", produces = {"application/json", "application/xml"})
public class ClientControllerMQ {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/all")
    public String findAll(@RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();

        if (mediaType.equals("json")) {
            rabbitTemplate.convertAndSend("request", "clients.all", mediaType);
            return "Message sent successfully to the queue.";
        } else if (mediaType.equals("xml")) {
            rabbitTemplate.convertAndSend("request", "clients.all", mediaType);
            return "Message sent successfully to the queue.";
        } else {
            return "Message was not sent to the queue. Wrong media type";
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
                String msg = mediaType + "/" + clientDTOToString;
                rabbitTemplate.convertAndSend("request", "clients.add", msg);
                return "Message sent successfully to the queue.";
            } else if (mediaType.equals("xml")) {
                XmlMapper mapper = new XmlMapper();
                String clientDTOToString = mapper.writeValueAsString(clientDTO);
                String msg = mediaType + "/" + clientDTOToString;
                rabbitTemplate.convertAndSend("request", "clients.add", msg);
                return "Message sent successfully to the queue.";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message was not sent to the queue. Wrong content type";
    }

    @GetMapping("/{id}")
    public String findClientById(@PathVariable(value = "id") int id, @RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();
        if (mediaType.equals("json") || mediaType.equals("xml")) {
            String msg = mediaType + "/" + id;
            rabbitTemplate.convertAndSend("request", "clients.showById", msg);
            return "Message sent successfully to the queue.";
        } else
            return "Message was not sent to the queue. Wrong media type";
    }

    @GetMapping("/{clientId}/orders")
    public String showOrdersByClientId(@PathVariable("clientId") long clientId, @RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();
        if (mediaType.equals("json") || mediaType.equals("xml")) {
            String msg = mediaType + "/" + clientId;
            rabbitTemplate.convertAndSend("request", "clients.showOrdersById", msg);
            return "Message sent successfully to the queue.";
        } else
            return "Message was not sent to the queue. Wrong media type";
    }


    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    @Transactional
    public String update(@RequestBody ClientDTO clientDTO, @RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();
        try {
            if (mediaType.equals("json")) {
                ObjectMapper mapper = new ObjectMapper();
                String clientDTOToString = mapper.writeValueAsString(clientDTO);
                String msg = mediaType + "/" + clientDTOToString;
                rabbitTemplate.convertAndSend("request", "clients.update", msg);
                return "Message sent successfully to the queue.";
            } else if (mediaType.equals("xml")) {
                XmlMapper mapper = new XmlMapper();
                String clientDTOToString = mapper.writeValueAsString(clientDTO);
                String msg = mediaType + "/" + clientDTOToString;
                rabbitTemplate.convertAndSend("request", "clients.update", msg);
                return "Message sent successfully to the queue.";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Message was not sent to the queue. Wrong content type";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") int id, @RequestHeader HttpHeaders headers) {
        String mediaType = headers.getAccept().get(0).getSubtype();
        if (mediaType.equals("json") || mediaType.equals("xml")) {
            String msg = mediaType + "/" + id;
            rabbitTemplate.convertAndSend("request", "clients.delete", msg);
            return "Message sent successfully to the queue.";
        } else
            return "Message was not sent to the queue. Wrong media type";

    }

}



