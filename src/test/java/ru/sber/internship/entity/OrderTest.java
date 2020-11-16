package ru.sber.internship.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sber.internship.entity.utils.OrderStatus;

import java.math.BigDecimal;

@SpringBootTest
class OrderTest {
    private Order order;
    private Client client;

    public OrderTest() {
        this.order = new Order();
        this.client = new Client();
    }


    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(order);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        Order order = new Order(1L, BigDecimal.ONE, OrderStatus.UNPAYED, client, null);
        Assert.assertNotNull(order);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        order.setId(1L);
        order.setOrderStatus(OrderStatus.UNPAYED);
        order.setTotalPrice(BigDecimal.ONE);
        order.setOrderItems(null);
        order.setClient(client);
        Assert.assertEquals(1L, (long) order.getId());
        Assert.assertEquals(OrderStatus.UNPAYED, order.getOrderStatus());
        Assert.assertEquals(client, order.getClient());
        Assert.assertNull(order.getOrderItems());
        Assert.assertEquals(BigDecimal.ONE, order.getTotalPrice());
    }

    @Test
    public void testBuilderAnnatation() {
        Order order = Order.builder()
                .id(1L)
                .client(client)
                .orderItems(null)
                .orderStatus(OrderStatus.UNPAYED)
                .totalPrice(BigDecimal.ONE)
                .build();
        Assert.assertEquals(1L, (long) order.getId());
        Assert.assertEquals(OrderStatus.UNPAYED, order.getOrderStatus());
        Assert.assertEquals(client, order.getClient());
        Assert.assertNull(null);
        Assert.assertEquals(BigDecimal.ONE, order.getTotalPrice());
    }
}