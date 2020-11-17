package ru.sber.internship.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderItemTest {

    private OrderItem orderItem;

    public OrderItemTest() {
        this.orderItem = new OrderItem();
    }

    @Test
    public void testAnnotationNoArgsConstructor() {
        Assert.assertNotNull(orderItem);
    }

    @Test
    public void testAnnotationAllAgrsConstructor() {
        OrderItem orderItem = new OrderItem(1L, 1, null, null);
        Assert.assertNotNull(orderItem);
    }

    @Test
    public void testGettersAndSettersAnnotatoin() {
        orderItem.setId(1L);
        orderItem.setCount(1);
        orderItem.setOrder(null);
        orderItem.setProduct(null);
        Assert.assertEquals(1L, (long) orderItem.getId());
        Assert.assertEquals(1, (int) orderItem.getCount());
        Assert.assertNull(orderItem.getOrder());
        Assert.assertNull(orderItem.getProduct());
    }

    @Test
    public void testBuilderAnnotation() {
        OrderItem orderItem = OrderItem.builder()
                .product(null)
                .count(1)
                .id(1L)
                .order(null)
                .build();
        Assert.assertEquals(1L, (long) orderItem.getId());
        Assert.assertEquals(1, (int) orderItem.getCount());
        Assert.assertNull(orderItem.getOrder());
        Assert.assertNull(orderItem.getProduct());
    }

}