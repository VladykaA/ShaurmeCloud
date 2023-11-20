package com.shaurmecloud.data;

import com.shaurmecloud.shaurme.ingredient.Ingredient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class IngredientRepoTest {

    @Autowired
    IngredientRepository repository;

    @Test
    public void findById(){
        Optional<Ingredient> flto = repository.findById("FLTO");
        Assertions.assertThat(flto.isPresent()).isTrue();
        Assertions.assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));

        Optional<Ingredient> xxx = repository.findById("XXX");
        Assertions.assertThat(xxx.isEmpty()).isTrue();
    }

}
