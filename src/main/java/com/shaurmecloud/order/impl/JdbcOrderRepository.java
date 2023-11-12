package com.shaurmecloud.order.impl;

import com.shaurmecloud.order.repo.OrderRepository;
import com.shaurmecloud.shaurme.Ingredient;
import com.shaurmecloud.shaurme.IngredientRef;
import com.shaurmecloud.shaurme.Shaurme;
import com.shaurmecloud.shaurme.ShaurmeOrder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public ShaurmeOrder save(ShaurmeOrder order) {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into Shaurme_Order "
                + "(delivery_name, delivery_phone, delivery_street, "
                + "delivery_apartment_number, delivery_entrance, "
                + "delivery_floor, cc_number, cc_expiration, cc_cvv, placed_at) "
                + "values (?,?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);

        factory.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());

        PreparedStatementCreator creator = factory.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryPhone(),
                        order.getDeliveryStreet(),
                        order.getDeliveryApartmentNumber(),
                        order.getDeliveryEntrance(),
                        order.getDeliveryFloor(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Shaurme> shaurmes = order.getShaurmes();
        int i = 0;
        for (Shaurme shaurme: shaurmes) {
            save(orderId, i++, shaurme);
        }
        return order;
    }

    private long save(Long orderId, int orderKey, Shaurme shaurme){
        shaurme.setPreparedAt(new Date());
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into Shaurme "
                + "(name, shaurme_order, shaurme_order_key, created_at) "
                + "values (?,?,?,?)",
                Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP);

        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator creator = factory.newPreparedStatementCreator(
                Arrays.asList(
                        shaurme.getName(),
                        orderId,
                        orderKey,
                        shaurme.getPreparedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long shaurmeId = keyHolder.getKey().longValue();
        shaurme.setId(shaurmeId);

        saveIngredientRef(shaurmeId, shaurme.getIngredients());

        return shaurmeId;
    }

    private void saveIngredientRef(long shaurmeId, List<IngredientRef> ingredientsRefs) {
        int key = 0;
        for(IngredientRef ingredientRef: ingredientsRefs){
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, shaurme, shaurme_key) "
                    + "values (?,?,?)",
                    ingredientRef.getIngredient(), shaurmeId, key++);
        }
    }
}
