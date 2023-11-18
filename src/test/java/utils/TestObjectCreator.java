package utils;

import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.users.entity.User;

public class TestObjectCreator {

    public static Car createNewCar(Long id, String make, String model, String numberplate) {
        Car car = new Car();
        car.setId(id);
        car.setMake(make);
        car.setModel(model);
        car.setNumberplate(numberplate);
        return car;
    }

    public static User createNewUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);

        return user;
    }
}
