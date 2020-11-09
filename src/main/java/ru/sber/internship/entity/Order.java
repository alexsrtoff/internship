package ru.sber.internship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sber.internship.entity.utils.PayConfirm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private BigDecimal tottalPrice;

    @Column
    private PayConfirm payConfirm;

    @ManyToOne
    private Client client;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}
