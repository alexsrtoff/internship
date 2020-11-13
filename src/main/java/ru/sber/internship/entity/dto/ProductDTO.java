package ru.sber.internship.entity.dto;

import lombok.Builder;
import lombok.Data;
import ru.sber.internship.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int discount;

}
