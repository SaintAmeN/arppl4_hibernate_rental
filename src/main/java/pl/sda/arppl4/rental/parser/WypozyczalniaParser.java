package pl.sda.arppl4.rental.parser;

import pl.sda.arppl4.rental.dao.GenericDao;
import pl.sda.arppl4.rental.model.Car;
import pl.sda.arppl4.rental.model.CarBodyType;
import pl.sda.arppl4.rental.model.CarGearBox;
import pl.sda.arppl4.rental.model.CarRental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WypozyczalniaParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Scanner scanner;
    private final GenericDao<Car> daoCar;
    private final GenericDao<CarRental> daoCarRental;

    public WypozyczalniaParser(Scanner scanner, GenericDao<Car> daoCar, GenericDao<CarRental> daoCarRental) {
        this.scanner = scanner;
        this.daoCar = daoCar;
        this.daoCarRental = daoCarRental;
    }

    public void wykonaj() {
        String komenda;
        do {
            System.out.println("Komenda");
            komenda = scanner.next();
            if (komenda.equalsIgnoreCase("dodaj")) {
                handleAddCommand();
            } else if (komenda.equalsIgnoreCase("zwroc")) {
                handleReturnCar();
            } else if (komenda.equalsIgnoreCase("lista")) {
                handleListCommand();
            } else if (komenda.equalsIgnoreCase("usun")) {
                handleDeleteCommand();
            } else if (komenda.equalsIgnoreCase("update")) {
                handleUpdateCommand();
            } else if (komenda.equalsIgnoreCase("dodajwynajem")) { // Dodaj wynajem
                handleAddRentCommand();
            }
            // Zwróć samochód ( z wynajmu )
            // Sprawdź dostępność samochodu


        } while (!komenda.equals("wyjdz"));
    }

    private void handleAddRentCommand() {
        System.out.println("Provide id of the car");
        Long id = scanner.nextLong();

        Optional<Car> samochodOptional = daoCar.znajdzPoId(id, Car.class);
        if (samochodOptional.isPresent()) {
            Car samochod = samochodOptional.get();

            System.out.println("Podaj imie");
            String name = scanner.next();

            System.out.println("Podaj nazwisko");
            String surname = scanner.next();

            LocalDateTime dataCzasWynajmu = LocalDateTime.now();
            System.out.println("Data i godzina wynajmu: " + dataCzasWynajmu);

            CarRental carRental = new CarRental(name, surname, dataCzasWynajmu, samochod);
            daoCarRental.dodaj(carRental);
        } else {
            System.out.println("Samochod nie znaleziony");
        }
    }

    private void handleDeleteCommand() {
        System.out.println("Provide id of the car");
        Long id = scanner.nextLong();

        Optional<Car> samochodOptional = daoCar.znajdzPoId(id, Car.class);
        if (samochodOptional.isPresent()) {
            Car samochod = samochodOptional.get();
            daoCar.usun(samochod);
            System.out.println("Samochod usuniety");
        } else {
            System.out.println("Samochod nie znaleziony");
        }
    }

    private void handleUpdateCommand() {
        System.out.println("Podaj id samochodu do aktualizacji:");
        Long id = scanner.nextLong();

        Optional<Car> samochodOptional = daoCar.znajdzPoId(id, Car.class);
        if (samochodOptional.isPresent()) {
            Car car = samochodOptional.get();

            System.out.println("Co chciałbys uakttualnic? [nazwa, date produkcji");
            String output = scanner.next();
            switch (output) {
                case "nazwa":
                    System.out.println("Podaj nazwe:");
                    String nazwa = scanner.next();

                    car.setName(nazwa);
                    break;
                case "dataProdukcji":
                    LocalDate dataWypozyczenia = zaladujDateProdukcji();

                    car.setProductionDate(dataWypozyczenia);
                    break;

                default:
                    System.out.println("Field with this name is not handled.");
            }

            daoCar.aktualizuj(car);
            System.out.println("Samochod zaktualizowano.");
        } else {

            System.out.println("Nie znaleziono samochodu");
        }
    }

    private void handleAddCommand() {
        System.out.println("Podaj nazwe samochodu");
        String nazwa = scanner.next();

        System.out.println("Podaj model");
        String model = scanner.next();

        LocalDate dataProdukcji = zaladujDateProdukcji();

        System.out.println("Podaj ilosc pasazerow:");
        Integer iloscPasazerow = scanner.nextInt();

        CarBodyType typNadwozia = zaladujTypNadwozia();

        CarGearBox typSkrzyni = zaladujTypSkrzyni();

        System.out.println("Podaj pojemnosc silnika:");
        Double pojemnoscSilnika = scanner.nextDouble();

        Car samochod = new Car(nazwa, model, dataProdukcji, typNadwozia, iloscPasazerow, typSkrzyni, pojemnoscSilnika);
        daoCar.dodaj(samochod);
    }

    private void handleListCommand() {
        List<Car> carList = daoCar.list(Car.class);
        for (Car samochod : carList) {
            // wypisz samochód, przez co wywołujemy pobranie relacji 'rental'
            System.out.println(samochod);
        }
        System.out.println();
    }

    private void handleReturnCar() {
        System.out.println("Podaj id samochodu");
        Long id = scanner.nextLong();

        Optional<Car> samochodOptional = daoCar.znajdzPoId(id, Car.class);
        if (samochodOptional.isPresent()) {
            Car samochod = samochodOptional.get();
            daoCar.usun(samochod);
            System.out.println("Car usuniety");
        } else {
            System.out.println("Nie znaleziono samochodu");
        }
    }

    private LocalDate zaladujDateProdukcji() {
        LocalDate dataProdukcji = null;
        do {
            try {
                System.out.println("Podaj date produkcji");
                String wprowadzonaDataWypozyczenia = scanner.next();

                dataProdukcji = LocalDate.parse(wprowadzonaDataWypozyczenia, FORMATTER);

                LocalDate today = LocalDate.now();
                if (dataProdukcji.isAfter(today)) {
                    throw new IllegalArgumentException(("Podano date późniejszą niz dzisiaj"));
                }

            } catch (IllegalArgumentException iae) {
                dataProdukcji = null;
                System.err.println("Wrong date, please provide date in format: yyy-MM-dd");
            }
        } while (dataProdukcji == null);
        return dataProdukcji;
    }

    private CarBodyType zaladujTypNadwozia() {
        CarBodyType typNadwozia = null;
        do {
            try {
                System.out.println("Podaj typ nadwozia");
                String unitString = scanner.next();

                typNadwozia = CarBodyType.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Nie ma takiego podwozia");
            }
        } while (typNadwozia == null);
        return typNadwozia;
    }

    private CarGearBox zaladujTypSkrzyni() {
        CarGearBox typSkrzyni = null;
        do {
            try {
                System.out.println("Podaj typ skrzyni");
                String unitString = scanner.next();

                typSkrzyni = CarGearBox.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Nie ma takiego podwozia");
            }
        } while (typSkrzyni == null);
        return typSkrzyni;
    }
}