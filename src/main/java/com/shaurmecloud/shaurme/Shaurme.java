package com.shaurmecloud.shaurme;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Shaurme {

    private Long id;

    private Date preparedAt = new Date();

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotNull
    @Size(min = 2, message = "At least two ingredients should be selected.")
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredients(Ingredient ingredient){
        this.ingredients.add(new IngredientRef(ingredient.getId()));
    }
}
