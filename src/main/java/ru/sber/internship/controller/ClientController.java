package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.service.impl.ClientServiceImpl;
import ru.sber.internship.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/clients", produces = {"application/json", "application/xml"})
public class ClientController {

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping
    public List<ClientDTO> findAll() {
        return clientService.convertClientListToClientDTOList(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ClientDTO findClientById(@PathVariable(value = "id") int id) {
        Client client = clientService.findById(id);
        if (client != null) {
            return clientService.convertClientToClientDTO(client);
        } else return new ClientDTO();
    }

    @GetMapping("/{clientId}/orders")
    public List<OrderDTO> showOrdersByClientId(@PathVariable("clientId") long clientId) {

        return orderService
                .convertOrderListToOrderDTOList(orderService.findAllByClient_Id(clientId));
    }

    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"})
    @Transactional
    public ClientDTO add(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.findById(clientDTO.getId());
        if (client != null) {
            return clientService.convertClientToClientDTO(client);
        }
        Client newClient = clientService.convertClientDTOToClient(clientDTO);
        return clientService.convertClientToClientDTO(newClient);
    }

    @PutMapping(value = "/update", consumes = {"application/json", "application/xml"})
    @Transactional
    public ClientDTO update(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.findById(clientDTO.getId());
        if (client.getId() == null) {
            return new ClientDTO();
        }
        clientService.convertClientDTOToClient(clientDTO);
        return clientDTO;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id) {
        return clientService.deleteById(id);
    }
}
