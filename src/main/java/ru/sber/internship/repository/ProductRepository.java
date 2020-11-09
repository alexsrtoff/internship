package ru.sber.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
}
