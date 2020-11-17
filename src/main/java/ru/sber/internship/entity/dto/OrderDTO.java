package ru.sber.internship.entity.dto;

import lombok.*;
import ru.sber.internship.entity.utils.OrderStatus;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) &&
                Objects.equals(totalPrice, orderDTO.totalPrice) &&
                orderStatus == orderDTO.orderStatus &&
                Objects.equals(clientId, orderDTO.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, orderStatus, clientId);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", clientId=" + clientId +
                '}';
    }
}
