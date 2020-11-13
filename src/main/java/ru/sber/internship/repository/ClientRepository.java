package ru.sber.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.OrderItem;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findById(long id);
}
