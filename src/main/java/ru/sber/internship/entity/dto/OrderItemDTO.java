package ru.sber.internship.entity.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    private Long id;
    private int count;
    private Long productId;
    private Long orderId;
}
