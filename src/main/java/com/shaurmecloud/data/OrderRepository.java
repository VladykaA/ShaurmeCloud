package com.shaurmecloud.data;

import com.shaurmecloud.shaurme.ShaurmeOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<ShaurmeOrder, Long> {

    List<ShaurmeOrder> findByDeliveryPhone(String deliveryPhone);

    List<ShaurmeOrder> readOrdersByDeliveryPhoneAndPlacedAtBetween(String deliveryPhone, Date startDate, Date endDate);
}
