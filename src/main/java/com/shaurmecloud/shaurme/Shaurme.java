package com.shaurmecloud.shaurme;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Shaurme {

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotNull
    @Size(min = 2, message = "At least two ingredients should be selected.")
    private List<Ingredient> ingredients;
}
