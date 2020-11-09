package ru.sber.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Client;
import ru.sber.internship.repository.ClientRepository;

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


    public void save(Client client) {
        clientRepository.save(client);
    }
}
