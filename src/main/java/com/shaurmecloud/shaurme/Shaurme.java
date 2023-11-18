package com.shaurmecloud.shaurme;

import com.shaurmecloud.shaurme.ingredient.Ingredient;
import com.shaurmecloud.shaurme.ingredient.IngredientRef;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@EqualsAndHashCode(exclude = "createdAt")
public class Shaurme {

    @Id
    private Long id;

    private Date createdAt = new Date();

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
