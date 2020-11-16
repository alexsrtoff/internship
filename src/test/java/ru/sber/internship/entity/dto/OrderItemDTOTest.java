package ru.sber.internship.entity.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderItemDTOTest {

    OrderItemDTO orderItemDTO;

    public OrderItemDTOTest() {
        this.orderItemDTO = new OrderItemDTO();
    }

    @Test
    public void testAnnotationNoArgsConstructor() {
        Assert.assertNotNull(orderItemDTO);
    }

    @Test
    public void testAnnotationAllAgrsConstructor() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 1, 1L, 1L);
        Assert.assertNotNull(orderItemDTO);
    }

    @Test
    public void testGettersAndSettersAnnotatoin() {
        orderItemDTO.setOrderId(1L);
        orderItemDTO.setProductId(1L);
        orderItemDTO.setId(1L);
        orderItemDTO.setCount(1);
        Assert.assertEquals(1L, (long) orderItemDTO.getId());
        Assert.assertEquals(1, orderItemDTO.getCount());
        Assert.assertEquals(1L, (long) orderItemDTO.getOrderId());
        Assert.assertEquals(1L, (long) orderItemDTO.getProductId());
    }

    @Test
    public void testBuilderAnnotation() {
        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .orderId(1L)
                .count(1)
                .productId(1L)
                .id(1L)
                .build();
        Assert.assertEquals(1L, (long) orderItemDTO.getId());
        Assert.assertEquals(1, orderItemDTO.getCount());
        Assert.assertEquals(1L, (long) orderItemDTO.getOrderId());
        Assert.assertEquals(1L, (long) orderItemDTO.getProductId());
    }

}