package ru.dm.shop.service;

import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.Order;

import java.util.List;

/**
 * Created by alt on 04.02.17.
 */
public interface OrderService {
    Order create(Cart cart);

    Order delete(long id);

    List<Order> findAll();

    Order update(Order order);

    Order findById(long id);
}
