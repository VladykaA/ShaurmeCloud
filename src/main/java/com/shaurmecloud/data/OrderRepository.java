package com.shaurmecloud.data;

import com.shaurmecloud.shaurme.ShaurmeOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<ShaurmeOrder, Long> {
}
