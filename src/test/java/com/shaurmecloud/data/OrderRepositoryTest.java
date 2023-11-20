package com.shaurmecloud.data;

import com.shaurmecloud.shaurme.Shaurme;
import com.shaurmecloud.shaurme.ShaurmeOrder;
import com.shaurmecloud.shaurme.ingredient.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class OrderRepositoryTest {

    private static final String DELIVERY_NAME = "Test Name";
    private static final String DELIVERY_PHONE = "1231231212";
    private static final String DELIVERY_STREET = "Test street";
    private static final String DELIVERY_APARTMENT_NUMBER = "1";
    private static final String CC_NUMBER = "4111111145551142";
    private static final String CC_EXPIRATION = "12/24";
    private static final String CC_CVV = "123";



    @Autowired
    OrderRepository repository;

    @Test
    public void saveOrderWithTwoShaurmes(){
        ShaurmeOrder shaurmeOrder = new ShaurmeOrder();
        shaurmeOrder.setDeliveryName(DELIVERY_NAME);
        shaurmeOrder.setDeliveryPhone(DELIVERY_PHONE);
        shaurmeOrder.setDeliveryStreet(DELIVERY_STREET);
        shaurmeOrder.setDeliveryApartmentNumber(DELIVERY_APARTMENT_NUMBER);
        shaurmeOrder.setCcNumber(CC_NUMBER);
        shaurmeOrder.setCcExpiration(CC_EXPIRATION);
        shaurmeOrder.setCcCVV(CC_CVV);

        Shaurme shaurmeOne = new Shaurme();
        shaurmeOne.setName("Test Shaurme one");
        shaurmeOne.addIngredients(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        shaurmeOne.addIngredients(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        shaurmeOne.addIngredients(new Ingredient("CHED", "Shredded Cheddar", Ingredient.Type.CHEESE));

        shaurmeOrder.addShaurme(shaurmeOne);

        Shaurme shaurmeTwo = new Shaurme();
        shaurmeTwo.setName("Test Shaurme Two");
        shaurmeTwo.addIngredients(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        shaurmeTwo.addIngredients(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        shaurmeTwo.addIngredients(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));

        shaurmeOrder.addShaurme(shaurmeTwo);

        ShaurmeOrder saveOrder = repository.save(shaurmeOrder);
        assertThat(saveOrder.getId()).isNotNull();

        ShaurmeOrder fetchOrder = repository.findById(saveOrder.getId()).get();
        assertThat(fetchOrder.getDeliveryName()).isEqualTo(DELIVERY_NAME);
        assertThat(fetchOrder.getDeliveryPhone()).isEqualTo(DELIVERY_PHONE);
        assertThat(fetchOrder.getDeliveryStreet()).isEqualTo(DELIVERY_STREET);
        assertThat(fetchOrder.getDeliveryApartmentNumber()).isEqualTo(DELIVERY_APARTMENT_NUMBER);
        assertThat(fetchOrder.getCcNumber()).isEqualTo(CC_NUMBER);
        assertThat(fetchOrder.getCcExpiration()).isEqualTo(CC_EXPIRATION);
        assertThat(fetchOrder.getCcCVV()).isEqualTo(CC_CVV);
        assertThat(fetchOrder.getPlacedAt().getTime()).isEqualTo(saveOrder.getPlacedAt().getTime());
        List<Shaurme> shaurmes = fetchOrder.getShaurmes();
        assertThat(shaurmes.size()).isEqualTo(2);
        assertThat(shaurmes).containsExactlyInAnyOrder(shaurmeOne, shaurmeTwo);
    }
}
