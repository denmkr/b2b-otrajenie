package ru.dm.shop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.OrderProduct;
import ru.dm.shop.repository.OrderProductRepository;
import ru.dm.shop.service.OrderProductService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by alt on 07.02.17.
 */

@Service
public class OrderProductServiceImpl implements OrderProductService {
    @Resource
    OrderProductRepository orderProductRepository;

    @Transactional
    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    @Override
    public OrderProduct delete(long id) {
        OrderProduct orderProduct = orderProductRepository.findOne(id);
        orderProductRepository.delete(orderProduct);
        return orderProduct;
    }

    @Override
    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    @Transactional
    @Override
    public OrderProduct update(OrderProduct orderProduct) {
        return orderProductRepository.saveAndFlush(orderProduct);
    }

    @Override
    public OrderProduct findById(long id) {
        return orderProductRepository.findOne(id);
    }
}
