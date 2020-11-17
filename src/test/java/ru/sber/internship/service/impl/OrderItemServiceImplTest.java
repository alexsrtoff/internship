package ru.sber.internship.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class OrderItemServiceImplTest {


    @BeforeEach
    void setUp() {
        System.out.println("HELLO");
    }

    @Test
    void findAll() {
        System.out.println("IN TEST HELLO");
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }

    @Test
    void save() {
    }

    @Test
    void findAllByOrderClientId() {
    }

    @Test
    void findAllByOrder_Id() {
    }

    @Test
    void findAllByProductId() {
    }

    @Test
    void getOrderItemsDTOFromRequest() {
    }

    @Test
    void convertOrderItemDTOToOrderItem() {
    }

    @Test
    void convertOrderItemToOrderItemDTO() {
    }

    @Test
    void convertListOrderItemToListOrderItemDTO() {
    }
}