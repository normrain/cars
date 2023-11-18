package com.example.hometask.domain.cars.reporitory;

import com.example.hometask.domain.cars.entity.Car;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findCarsByUserId(Long userId);

}
