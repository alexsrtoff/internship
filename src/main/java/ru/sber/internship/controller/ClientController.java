package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Client;
import ru.sber.internship.service.impl.ClientServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientServiceImpl clientService;


    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client findClientById(@PathVariable(value = "id", required = true) int id) {
        if (clientService.findById(id) != null) {
            return clientService.findById(id);
        } else return new Client();
    }


    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody Client client) {
        if (client.getId() != null) {
            throw new IllegalArgumentException("Id found in the create reuest");
        }
        clientService.save(client);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    private void update(@RequestBody Client client) {
        if (client.getId() == null) {
            throw new IllegalArgumentException("Id not found in the create reuest");
        }
        clientService.save(client);

    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id", required = true) int id) {
        boolean flag = false;
        if (clientService.findById(id) != null) {
            clientService.deleteById(id);
            flag = true;
        }
        return flag;
    }
}
