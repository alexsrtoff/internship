package ru.sber.internship.entity.dto;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class OrderItemDTO {

    private Long id;
    private int count;
    private long productId;
}
