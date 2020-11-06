package ru.sber.internship.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

//    @Override
//    public String toString() {
//        return "Client{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
