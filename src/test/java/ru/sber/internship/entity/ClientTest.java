package ru.sber.internship.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest
class ClientTest {

    private Client client;

    public ClientTest() {
        this.client = new Client();
    }


    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(client);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        Client client = new Client(1L, "ivan", "ivanov", "ivanov@mail.ru", null);
        Assert.assertNotNull(client);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        client.setId(1L);
        client.setFirstName("ivan");
        client.setLastName("ivanov");
        client.setEmail("ivanov@mail.ru");
        client.setOrders(null);
        Assert.assertEquals(1L, (long) client.getId());
        Assert.assertEquals("ivan", client.getFirstName());
        Assert.assertEquals("ivanov", client.getLastName());
        Assert.assertEquals("ivanov@mail.ru", client.getEmail());
        Assert.assertNull(client.getOrders());
    }

    @Test
    public void testBuilderAnnatation() {
        Client client = Client.builder().firstName("ivan").lastName("ivanov").email("ivanov@mail.ru").orders(null).build();
        Assert.assertEquals("ivan", client.getFirstName());
        Assert.assertEquals("ivanov", client.getLastName());
        Assert.assertEquals("ivanov@mail.ru", client.getEmail());
        Assert.assertNull(client.getOrders());
    }
}