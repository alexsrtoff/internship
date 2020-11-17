package ru.sber.internship.entity.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientDTOTest {

    ClientDTO clientDTO;

    public ClientDTOTest() {
        this.clientDTO = new ClientDTO();
    }

    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(clientDTO);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        ClientDTO clientDTO = new ClientDTO(1L, "ivan", "ivanov", "ivaniv@mail.ru");
        Assert.assertNotNull(clientDTO);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        clientDTO.setFirstName("ivan");
        clientDTO.setLastName("ivanov");
        clientDTO.setEmail("ivanov@mail.ru");
        clientDTO.setId(1L);
        Assert.assertEquals("ivan", clientDTO.getFirstName());
        Assert.assertEquals("ivanov", clientDTO.getLastName());
        Assert.assertEquals("ivanov@mail.ru", clientDTO.getEmail());
        Assert.assertEquals(1, (long) clientDTO.getId());
    }

    @Test
    public void testBuilderAnnatation() {
        ClientDTO clientDTO = ClientDTO.builder().id(1L).firstName("ivan").lastName("ivanov").email("ivanov@mail.ru").build();
        Assert.assertEquals(1L, (long) clientDTO.getId());
        Assert.assertEquals("ivan", clientDTO.getFirstName());
        Assert.assertEquals("ivanov", clientDTO.getLastName());
        Assert.assertEquals("ivanov@mail.ru", clientDTO.getEmail());
    }


}