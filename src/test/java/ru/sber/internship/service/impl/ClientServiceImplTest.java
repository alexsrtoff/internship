package ru.sber.internship.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockBean
    ClientRepository clientRepository;

    @Test
    void findAll() {
        List<Client> clients = new ArrayList<>();
        clients.add(Client.builder().firstName("ivan").lastName("ivanov").email("ivan@mail.ru").build());
        clients.add(Client.builder().firstName("petr").lastName("petrov").email("petr@mail.ru").build());
        Mockito.when(clientService.findAll()).thenReturn(clients);
        List<Client> clientsFrom = clientService.findAll();
        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(clients, clientsFrom);
    }

    @Test
    void save() {
        Client client = new Client(null, "Ivan", "Ivanov", "ivanivanov@mail.ru", null);
        Mockito.when(clientService.save(client)).thenReturn(client);
        Client saveClient = clientService.save(client);
        Mockito.verify(clientRepository, Mockito.times(1)).save(client);
        Assert.assertEquals(client, saveClient);
    }


    @Test
    void findById() {
        Client client = new Client(1L, "Ivan", "Ivanov", "ivanivanov@mail.ru", null);
        Mockito.when(clientService.findById(1)).thenReturn(client);
        Client client1 = clientService.findById(1);
        Mockito.verify(clientRepository, Mockito.times(1)).findById(1);
        Assert.assertEquals(client, client1);

        Mockito.when(clientService.findById(10L)).thenReturn(null);
        Client client2 = clientService.findById(10L);
        Mockito.verify(clientRepository, Mockito.times(1)).findById(10L);
        Assert.assertNull(client2);
    }

    @Test
    void deleteById() {
        Client client = new Client(1L, "Ivan", "Ivanov", "ivanivanov@mail.ru", null);
        Mockito.when(clientService.findById(1)).thenReturn(client);
        boolean deleteTrue = clientService.deleteById(1);
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(1L);
        Assert.assertTrue(deleteTrue);

        Mockito.when(clientService.findById(10)).thenReturn(null);
        boolean deleteFalse = clientService.deleteById(10);
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(1L);
        Assert.assertFalse(deleteFalse);
    }


    @Test
    void convertClientToClientDTO() {
        Client client = new Client(1L, "Ivan", "Ivanov", "ivanivanov@mail.ru", null);
        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .build();
        ClientDTO convertedClient = clientService.convertClientToClientDTO(client);
        Assert.assertEquals(clientDTO, convertedClient);
    }

    @Test
    void convertClientListToClientDTOList() {
        Client client1 = new Client(1L, "Ivan1", "Ivanov1", "ivanivanov1@mail.ru", null);
        Client client2 = new Client(2L, "Ivan2", "Ivanov2", "ivanivanov2@mail.ru", null);

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        List<ClientDTO> clientDTOS = new ArrayList<>();
        clientDTOS.add(clientService.convertClientToClientDTO(client1));
        clientDTOS.add(clientService.convertClientToClientDTO(client2));

        List<ClientDTO> convertedClients = clientService.convertClientListToClientDTOList(clients);
        Assert.assertEquals(clientDTOS, convertedClients);

    }

    @Test
    void convertClientDTOToClient() {
        Client client = new Client(1L, "Ivan1", "Ivanov1", "ivanivanov1@mail.ru", null);
        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .build();
        Mockito.when(clientService.convertClientDTOToClient(clientDTO)).thenReturn(client);

        Client client1 = clientService.convertClientDTOToClient(clientDTO);
        Assert.assertEquals(client, client1);
    }
}