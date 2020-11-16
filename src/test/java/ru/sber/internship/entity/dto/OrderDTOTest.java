package ru.sber.internship.entity.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sber.internship.entity.utils.OrderStatus;

import java.math.BigDecimal;

@SpringBootTest
class OrderDTOTest {

    OrderDTO orderDTO;

    public OrderDTOTest() {
        this.orderDTO = new OrderDTO();
    }

    @Test
    public void testAnnatationForNoArgsConstructor() {
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void testAnnatationForAllArgsConstructor() {
        OrderDTO orderDTO = new OrderDTO(1L, BigDecimal.ONE, OrderStatus.UNPAYED, 1L);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void testAnnotationForGettersAndSetters() {
        orderDTO.setId(1L);
        orderDTO.setClientId(1L);
        orderDTO.setOrderStatus(OrderStatus.UNPAYED);
        orderDTO.setTotalPrice(BigDecimal.ONE);
        Assert.assertEquals(1L, (long) orderDTO.getId());
        Assert.assertEquals(1L, (long) orderDTO.getClientId());
        Assert.assertEquals(OrderStatus.UNPAYED, orderDTO.getOrderStatus());
        Assert.assertEquals(BigDecimal.ONE, orderDTO.getTotalPrice());
    }

    @Test
    public void testBuilderAnnatation() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .clientId(1L)
                .totalPrice(BigDecimal.ONE)
                .orderStatus(OrderStatus.UNPAYED)
                .build();

        Assert.assertEquals(1L, (long) orderDTO.getId());
        Assert.assertEquals(1L, (long) orderDTO.getClientId());
        Assert.assertEquals(OrderStatus.UNPAYED, orderDTO.getOrderStatus());
        Assert.assertEquals(BigDecimal.ONE, orderDTO.getTotalPrice());
    }


}