package com.example.hometask.domain.cars.reporitory;

import com.example.hometask.domain.cars.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    public List<Car> findCarsByUserId(Long userId);

}
