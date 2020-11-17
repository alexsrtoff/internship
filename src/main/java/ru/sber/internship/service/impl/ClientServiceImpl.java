package ru.sber.internship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.repository.ClientRepository;
import ru.sber.internship.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrderServiceImpl orderService;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id);
    }


    public boolean deleteById(long id) {
        if (clientRepository.findById(id) == null) {
            return false;
        }
        clientRepository.deleteById(id);
        return true;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public ClientDTO convertClientToClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
    }

    public List<ClientDTO> convertClientListToClientDTOList(List<Client> clientList) {
        return clientList.stream().map(c -> convertClientToClientDTO(c)).collect(Collectors.toList());
    }

    public Client convertClientDTOToClient(ClientDTO clientDTO) {
        return save(Client.builder()
                .id(clientDTO.getId())
                .firstName(clientDTO.getFirstName())
                .lastName(clientDTO.getLastName())
                .email(clientDTO.getEmail())
                .orders(orderService.findAllByClient_Id(clientDTO.getId()))
                .build());
    }
}
