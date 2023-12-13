package com.shaurmecloud.shaurme;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ShaurmeOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt = new Date();

    @NotBlank(message = "Name is required.")
    private String deliveryName;

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Not valid phone number.")
    private String deliveryPhone;

    @NotBlank(message = "Street is required.")
    private String deliveryStreet;

    @NotBlank(message = "Apartment/house number is required.")
    private String deliveryApartmentNumber;

    private String deliveryEntrance;

    private String deliveryFloor;

    @CreditCardNumber(message = "Not valid credit card number.")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Shaurme> shaurmes = new ArrayList<>();

    public void addShaurme(Shaurme shaurme) {
        shaurmes.add(shaurme);
    }
}
