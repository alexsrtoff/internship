package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Client;
import ru.sber.internship.repository.ClientRepository;
import ru.sber.internship.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        List<Client> all = clientRepository.findAll();
        return all;
    }

    @Override
    public Client findById(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        clientRepository.deleteById(id);
    }


    public void save(Client client) {
        clientRepository.save(client);
    }
}
