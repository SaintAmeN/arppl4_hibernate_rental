package pl.sda.arppl4.rental;

import pl.sda.arppl4.rental.dao.GenericDao;
import pl.sda.arppl4.rental.model.Car;
import pl.sda.arppl4.rental.model.CarRental;
import pl.sda.arppl4.rental.parser.WypozyczalniaParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GenericDao<Car> carGenericDao = new GenericDao<>();
        GenericDao<CarRental> carRentalGenericDao = new GenericDao<>();

        WypozyczalniaParser parser = new WypozyczalniaParser(scanner, carGenericDao, carRentalGenericDao);
        parser.wykonaj();
    }
}
