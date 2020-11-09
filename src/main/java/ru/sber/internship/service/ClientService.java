package ru.sber.internship.service;

import ru.sber.internship.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(int id);
    void deleteById(int id);
}
