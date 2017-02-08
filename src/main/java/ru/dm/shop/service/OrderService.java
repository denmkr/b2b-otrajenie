package ru.dm.shop.service;

import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.Order;
import ru.dm.shop.entity.User;

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

    List<Order> findAllByUser(User user);
}
