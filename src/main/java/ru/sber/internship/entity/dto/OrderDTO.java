package ru.sber.internship.entity.dto;

import lombok.*;
import ru.sber.internship.entity.utils.OrderStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDTO {
    private Long id;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private Long clientId;


}
