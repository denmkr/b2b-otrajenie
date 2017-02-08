package ru.dm.shop.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.*;
import ru.dm.shop.repository.OrderRepository;
import ru.dm.shop.service.OrderProductService;
import ru.dm.shop.service.OrderService;
import ru.dm.shop.service.UserRoleService;
import ru.dm.shop.service.UserService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by alt on 04.02.17.
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    public OrderRepository orderRepository;

    @Resource
    OrderProductService orderProductService;

    @Resource
    UserService userService;

    @Resource
    UserRoleService userRoleService;

    @Override
    @Transactional
    public Order create(Cart cart) {
        Order order = new Order();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(authentication.getName());
        order.setUser(user);
        order.setDate(new Timestamp(System.currentTimeMillis()));

        List<CartProduct> cartProducts = cart.getCartProducts();

        for (CartProduct cartProduct : cartProducts) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getCount());

            if (user != null && userRoleService.findByUserId(user.getId()).getAuthority().equals("ROLE_PARTNER") ) {
                orderProduct.setPrice(cartProduct.getProduct().getWholesalePrice());
            } else {
                orderProduct.setPrice(cartProduct.getProduct().getRetailPrice());
            }

            orderProduct.setOrder(order);
            orderProductService.create(orderProduct);
        }
        return  orderRepository.saveAndFlush(order);
    }

    @Override
    @Transactional
    public Order delete(long id) {
        Order order = orderRepository.findOne(id);
        orderRepository.delete(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order update(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findOne(id);
    }
}