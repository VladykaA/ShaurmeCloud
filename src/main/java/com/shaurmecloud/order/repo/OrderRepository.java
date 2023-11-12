package com.shaurmecloud.order.repo;

import com.shaurmecloud.shaurme.ShaurmeOrder;

public interface OrderRepository {
    ShaurmeOrder save(ShaurmeOrder order);
}
