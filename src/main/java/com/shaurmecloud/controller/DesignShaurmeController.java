package com.shaurmecloud.controller;

import com.shaurmecloud.shaurme.Ingredient;
import com.shaurmecloud.shaurme.Shaurme;
import com.shaurmecloud.shaurme.ShaurmeOrder;
import com.shaurmecloud.shaurme.ingredient.repo.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static com.shaurmecloud.shaurme.Ingredient.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("shaurmeOrder")
public class DesignShaurmeController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignShaurmeController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List <Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "shaurmeOrder")
    public ShaurmeOrder order() {
        return new ShaurmeOrder();
    }

    @ModelAttribute(name = "shaurme")
    public Shaurme shaurme(){
        return new Shaurme();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processShaurme(@Valid Shaurme shaurme, Errors errors, @ModelAttribute ShaurmeOrder order){
        order.addShaurme(shaurme);
        log.info("Processing shaurme: {}", shaurme);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
