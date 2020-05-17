package web.service;

import org.springframework.stereotype.Component;
import web.model.Car;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarService {
    private List<Car> list = addCars();

    public CarService() {

    }

    private List<Car> addCars() {
        List<Car> list = new ArrayList<>();
        list.add(new Car("rx", "audi", 111));
        list.add(new Car("mx", "bmw", 222));
        list.add(new Car("ry", "lada", 333));
        list.add(new Car("rk", "tesla", 444));
        return list;
    }

    public List<Car> getCars() {
        return list;
    }
}
