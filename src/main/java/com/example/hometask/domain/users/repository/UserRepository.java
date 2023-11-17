package com.example.hometask.domain.users.repository;

import com.example.hometask.domain.users.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> { }
