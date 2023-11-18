package com.shaurmecloud.data;

import com.shaurmecloud.shaurme.ingredient.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
