package ru.sber.internship.entity.dto;

import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemDTO)) return false;
        OrderItemDTO that = (OrderItemDTO) o;
        return count == that.count &&
                Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, productId, orderId);
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", count=" + count +
                ", productId=" + productId +
                ", orderId=" + orderId +
                '}';
    }
}
