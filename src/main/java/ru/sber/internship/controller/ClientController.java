package ru.sber.internship.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Client;
import ru.sber.internship.service.impl.ClientServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Api(value = "Clients", description = "Operations related to clients")
public class ClientController {

    @Autowired
    ClientServiceImpl clientService;

    @GetMapping
    @ApiOperation(value = "Show all clients")
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @ApiOperation(value = "Search a client with an ID")
    @GetMapping("/{id}")
    public Client findClientById(@PathVariable(value = "id", required = true) int id) {
        if (clientService.findById(id) != null) {
            return clientService.findById(id);
        } else return new Client();
    }

    @ApiOperation(value = "Add new client")
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Client add(@RequestBody Client client) {
        if (client.getId() != null) {
            throw new IllegalArgumentException("Id found in the create reuest");
        }
        return clientService.save(client);
    }

    @ApiOperation(value = "Update a client")
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private Client update(@RequestBody Client client) {
        if (client.getId() == null) {
            throw new IllegalArgumentException("Id not found in the create reuest");
        }
        return clientService.save(client);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete client")
    public boolean delete(@PathVariable(value = "id") int id) {
        return clientService.deleteById(id);
    }
}
