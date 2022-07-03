package pl.sda.arppl4.rental;

import pl.sda.arppl4.rental.dao.GenericDao;
import pl.sda.arppl4.rental.model.Car;

import java.util.Optional;

public class MainFetchType {
    public static void main(String[] args) {
        GenericDao<Car> carGenericDao = new GenericDao<Car>();

        Optional<Car> carOptional = carGenericDao.znajdzPoId(1L, Car.class);
        if (carOptional.isPresent()){
            System.out.println("Znalazłem samochód");
            Car car = carOptional.get();  // managed object

            System.out.println(car.getCarRentals()); // trigger dla entity managera aby pobrać tabele relacyjną

        }
    }
}
