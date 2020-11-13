package ru.sber.internship.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.internship.entity.utils.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private Long clientId;

//    private List<OrderItemDTO> items;


}
