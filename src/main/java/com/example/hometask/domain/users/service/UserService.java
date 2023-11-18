package com.example.hometask.domain.users.service;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.domain.users.entity.User;
import com.example.hometask.domain.users.repository.UserRepository;
import com.example.hometask.util.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hometask.util.Sorting.SortingUtil.createSorting;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CarService carService;

    public UserResponse getUserWithId(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new EntityNotFoundException("User", id);
        }
        List<CarResponse> carResponse = carService.getCarsForUser(user.getId());

        return UserResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .cars(carResponse)
                .build();

    }

    public List<UserResponse> getUsersWithCars(String find, String sort) {
        List<User> users = (sort != null) ? userRepository.findAll(createSorting(sort)) : userRepository.findAll();

        if(find != null) {
            return mapFilteredUserEntityToResponse(users, find);
        } else {
            return mapUserEntityToResponse(users);
        }

    }

    private List<UserResponse> mapUserEntityToResponse(List<User> users) {
        return users.stream().map(
                user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .cars(carService.getCarsForUser(user.getId()))
                        .build()
        ).collect(Collectors.toList());
    }

    private List<UserResponse> mapFilteredUserEntityToResponse(List<User> users, String find) {
        return users.stream()
                .filter(user -> user.getName().contains(find))
                .map(
                user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .cars(carService.getCarsForUser(user.getId()))
                        .build()
        ).collect(Collectors.toList());
    }
}
