package ru.sber.internship.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.internship.entity.Client;
import ru.sber.internship.repository.ClientRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/delete-data-from-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ClientServiceImplTest {

    @Autowired
    ClientServiceImpl clientService;

    @MockBean
    ClientRepository clientRepository;

    @Test
    void findAll() {
        List<Client> clients = clientService.findAll();
        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(clients);

    }

//    @Test
//    void findById() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void convertClientToClientDTO() {
//    }
//
//    @Test
//    void convertClientListToClientDTOList() {
//    }
//
//    @Test
//    void convertClientDTOToClient() {
//    }
}