package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.internship.entity.Client;
import ru.sber.internship.service.ClientServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ClientController {

    @Autowired
    ClientServiceImpl clientService;

    @GetMapping("/s")
    public Client findAll() {
        List<Client> all = new ArrayList<>();
        all = clientService.findAll();
        System.out.println(all.getClass());
        System.out.println(clientService.findAll());
        return all.get(0);
    }

    @GetMapping
    public List<Client> findAll1() {
        List<Client> all = new ArrayList<>();
        all = clientService.findAll();
        System.out.println(all.getClass());
        System.out.println(clientService.findAll());
        return all;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody Client client) {
        System.out.println(client.getFirstName());
        clientService.save(client);
    }
}
