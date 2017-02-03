package ru.dm.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.CartProduct;
import ru.dm.shop.entity.Order;
import ru.dm.shop.repository.OrderRepository;
import ru.dm.shop.service.OrderService;
import ru.dm.shop.service.UserService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    public OrderRepository orderRepository;
    @Autowired
    UserService userService;
    public Authentication authentication;

    @Override
    public boolean createOrder(Cart cart) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Order order = new Order();

        for (CartProduct cartProduct : cart.getCartProducts()) {
            order.setProduct(cartProduct.getProduct());
            order.setCount(1);
            order.setUser(userService.findByUsername(authentication.getName()));

            java.util.Date date = new java.util.Date();
            order.setDate(new Timestamp(date.getTime()));

            orderRepository.saveAndFlush(order);
        }

        return true;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Long countOfOrdersToday() {
        return orderRepository.countOfOrdersToday();
    }

    @Override
    public List<Long> getOrdersCounts() {
        List<Long> counts = new ArrayList<Long>();
        for (int i=1; i<=30; i++) {
            counts.add(orderRepository.countOfOrdersByDay(i));
        }

        return counts;
    }
}