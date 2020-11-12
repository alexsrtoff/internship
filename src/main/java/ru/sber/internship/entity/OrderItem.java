package ru.sber.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "count"})
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }
}
