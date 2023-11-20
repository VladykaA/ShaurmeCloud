package com.shaurmecloud.web;

import com.shaurmecloud.data.IngredientRepository;
import com.shaurmecloud.shaurme.ingredient.Ingredient;
import com.shaurmecloud.shaurme.ingredient.Ingredient.Type;

import com.shaurmecloud.shaurme.ingredient.IngredientByIdConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientByIdConverterTest {

    private IngredientByIdConverter converter;

    @BeforeEach
    public void setup(){
        IngredientRepository repository = mock(IngredientRepository.class);
        when(repository.findById("AAA"))
                .thenReturn(Optional.of(new Ingredient("AAA", "Test ingredient", Type.SAUCE)));

        when(repository.findById("XXX"))
                .thenReturn(Optional.empty());

        this.converter = new IngredientByIdConverter(repository);
    }

    @Test
    public void shouldReturnPresentValue(){
        assertThat(converter.convert("AAA"))
                .isEqualTo(new Ingredient("AAA", "Test ingredient", Ingredient.Type.SAUCE));
    }
    @Test
    public void shouldReturnNull(){
        assertThat(converter.convert("XXX")).isNull();
    }
}
