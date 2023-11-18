package com.shaurmecloud.controller;

import com.shaurmecloud.shaurme.Shaurme;
import com.shaurmecloud.shaurme.ShaurmeOrder;
import com.shaurmecloud.shaurme.ingredient.Ingredient;
import com.shaurmecloud.data.IngredientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shaurmecloud.shaurme.ingredient.Ingredient.Type;

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
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

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
    public Shaurme shaurme() {
        return new Shaurme();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processShaurme(@Valid Shaurme shaurme, Errors errors, @ModelAttribute ShaurmeOrder order) {
        if (errors.hasErrors()) {
            return "/design";
        }
        order.addShaurme(shaurme);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
