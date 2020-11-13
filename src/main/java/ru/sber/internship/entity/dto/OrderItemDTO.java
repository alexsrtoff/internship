package ru.sber.internship.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.LocaleType;
import ru.sber.internship.entity.Product;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;
    private int count;
    private Long productId;
    private Long orderId;
}
