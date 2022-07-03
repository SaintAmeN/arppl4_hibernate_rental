package pl.sda.arppl4.rental;

import pl.sda.arppl4.rental.dao.GenericDao;
import pl.sda.arppl4.rental.model.Car;
import pl.sda.arppl4.rental.parser.WypozyczalniaParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GenericDao<Car> carGenericDao = new GenericDao<>();

        WypozyczalniaParser parser = new WypozyczalniaParser(scanner, carGenericDao);
        parser.wykonaj();
    }
}
