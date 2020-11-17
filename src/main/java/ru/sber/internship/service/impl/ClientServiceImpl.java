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

    /**
     * finds all Clients
     *
     * @return
     */
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    /**
     * finds a Client by Id
     *
     * @param id
     * @return
     */
    @Override
    public Client findById(long id) {
        return clientRepository.findById(id);
    }

    /**
     * deletes Client by Id
     *
     * @param id
     * @return
     */
    public boolean deleteById(long id) {
        if (clientRepository.findById(id) == null) {
            return false;
        }
        clientRepository.deleteById(id);
        return true;
    }

    /**
     * Save client
     *
     * @param client
     * @return
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    /**
     * Converts Client to ClienDTO
     *
     * @param client
     * @return
     */
    public ClientDTO convertClientToClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
    }

    /**
     * Converts the Client list to the ClientDTO list
     *
     * @param clientList
     * @return
     */
    public List<ClientDTO> convertClientListToClientDTOList(List<Client> clientList) {
        return clientList.stream().map(c -> convertClientToClientDTO(c)).collect(Collectors.toList());
    }

    /**
     * Converts Client to ClienDTO and save it
     *
     * @param clientDTO
     * @return
     */
    public Client convertClientDTOToClient(ClientDTO clientDTO) {
        return save(Client.builder()
                .id(clientDTO.getId())
                .firstName(clientDTO.getFirstName())
                .lastName(clientDTO.getLastName())
                .email(clientDTO.getEmail())
                .orders(orderService.findAllByClientId(clientDTO.getId()))
                .build());
    }
}
