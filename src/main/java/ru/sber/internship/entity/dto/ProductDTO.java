package ru.sber.internship.entity.dto;

import lombok.*;
import ru.sber.internship.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int discount;

}
