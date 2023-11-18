package com.example.hometask.util.Response;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.users.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResponseUtil {

    public static Sort createSorting(String sort) {
        String[] parts = sort.split(":");
        if(Objects.equals(parts[1], "asc")){
            return Sort.by(Sort.Direction.ASC, parts[0]);
        } else {
            return Sort.by(Sort.Direction.DESC, parts[0]);
        }
    }

    public static List<CarResponse> mapCarEntityToResponse(List<Car> cars) {
        return cars.stream().map(
                car -> CarResponse.builder()
                        .id(car.getId())
                        .make(car.getMake())
                        .model(car.getModel())
                        .numberplate(car.getNumberplate())
                        .build()
        ).collect(Collectors.toList());
    }

    public static List<CarResponse> mapFilteredCarEntityToResponse(List<Car> cars, String find) {
        return cars.stream()
                .filter(car -> car.getMake().contains(find) ||
                        car.getModel().contains(find)
                        || car.getNumberplate().contains(find))
                .map(
                        car -> CarResponse.builder()
                                .id(car.getId())
                                .make(car.getMake())
                                .model(car.getModel())
                                .numberplate(car.getNumberplate())
                                .build()
                ).collect(Collectors.toList());
    }


}
