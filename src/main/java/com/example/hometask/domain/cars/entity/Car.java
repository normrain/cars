package com.example.hometask.domain.cars.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "make")
    private String make;
    @Column(name = "model")
    private String model;
    @Column(name = "number_plate")
    private String numberplate;
    @Column(name = "user_id")
    private Long userId;
}