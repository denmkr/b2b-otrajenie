package ru.dm.shop.service;

import ru.dm.shop.entity.OrderProduct;

import java.util.List;

/**
 * Created by alt on 07.02.17.
 */
public interface OrderProductService  {
    OrderProduct create(OrderProduct orderProduct);

    OrderProduct delete(long id);

    List<OrderProduct> findAll();

    OrderProduct update(OrderProduct orderProduct);

    OrderProduct findById(long id);
}
