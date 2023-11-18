package com.example.hometask.domain.users.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name ="users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
}
