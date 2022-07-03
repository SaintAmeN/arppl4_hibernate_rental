package pl.sda.arppl4.rental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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

}
