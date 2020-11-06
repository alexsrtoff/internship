package ru.sber.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.internship.entity.Client;
import ru.sber.internship.service.ClientServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientServiceImpl clientService;

    @GetMapping
    public Client findAll(){
        List<Client> all = new ArrayList<>();
        all = clientService.findAll();
        System.out.println(all.getClass());
        System.out.println(clientService.findAll());
        return all.get(0);
    }


}
