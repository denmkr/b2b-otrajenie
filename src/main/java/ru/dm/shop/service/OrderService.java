package ru.dm.shop.service;

import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.Order;

import java.util.List;

/**
 * Created by Denis on 17.04.16.
 */
public interface OrderService {
    boolean createOrder(Cart cart);
    List<Order> getOrders();

    Long countOfOrdersToday();

    List<Long> getOrdersCounts();
}

