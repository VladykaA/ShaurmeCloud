package com.shaurmecloud.shaurme;

import com.shaurmecloud.shaurme.ingredient.Ingredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Shaurme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotNull
    @Size(min = 2, message = "At least two ingredients should be selected.")
    @ManyToMany()
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredients(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
}
