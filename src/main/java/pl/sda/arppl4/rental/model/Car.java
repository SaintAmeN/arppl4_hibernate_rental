package pl.sda.arppl4.rental.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data // Getter Setter ToString EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    ///
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ///

    private String name;
    private String make;
    private LocalDate productionDate;

    @Enumerated(EnumType.STRING)
    private CarBodyType bodyType;

    private Integer seats;

    @Enumerated(EnumType.STRING)
    private CarGearBox carGearBox;

    private Double engineCapacity;

    ///
    // select * from car c join rental r on ...
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<CarRental> carRentals;

    public Car(String name, String make, LocalDate productionDate, CarBodyType bodyType, Integer seats, CarGearBox carGearBox, Double engineCapacity) {
        this.name = name;
        this.make = make;
        this.productionDate = productionDate;
        this.bodyType = bodyType;
        this.seats = seats;
        this.carGearBox = carGearBox;
        this.engineCapacity = engineCapacity;
    }
}
