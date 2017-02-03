package ru.dm.shop.controller;

/**
 * Created by Denis on 05.05.16.
 */

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.dm.shop.entity.*;
import ru.dm.shop.service.*;


import java.util.List;

@Controller
@RequestMapping("/api")
public class RESTController {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRoleService userRoleService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public @ResponseBody
    boolean addUser(ModelMap model,
                    @RequestParam(value = "name", required = true) String name,
                    @RequestParam(value = "password", required = true) String password,
                    @RequestParam(value = "email", required = true) String email,
                    @RequestParam(value = "role", required = true) String role) {
        User user = new User();
        user.setUsername(StringEscapeUtils.escapeHtml4(name));
        user.setEmail(StringEscapeUtils.escapeHtml4(email));
        user.setPassword(StringEscapeUtils.escapeHtml4(password));

        userService.create(user);

        if (role.equals("Администратор")) userRoleService.createAdmin(user);
        if (role.equals("Партнер")) userRoleService.createPartner(user);
        if (role.equals("Обычный пользователь")) userRoleService.createUser(user);

        return true;
    }

    @RequestMapping(value = "/users/remove", method = RequestMethod.POST)
    public @ResponseBody
    boolean removeUser(ModelMap model, @RequestParam(value = "userId", required = true) String userId) {
        userRoleService.deleteByUserId(Long.parseLong(userId));
        userService.delete(Long.parseLong(userId));

        return true;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductGroup> getAllGroups() {
        List<ProductGroup> groups = groupService.findAll();

        for (ProductGroup group : groups) {
            group.setChildGroups(null);
            group.setParentGroup(null);
        }

        return groups;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public @ResponseBody
    List<Order> getAllOrders() {
        List<Order> orders = orderService.getOrders();

        for (Order order : orders) {
            order.getProduct().getProductGroup().setChildGroups(null);
            order.getProduct().getProductGroup().setParentGroup(null);
        }

        return orders;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody
    List<Product> getAllProducts() {
        List<Product> products = productService.findAll();

        for (Product product : products) {
            product.getProductGroup().setChildGroups(null);
            product.getProductGroup().setParentGroup(null);
        }

        return products;
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public @ResponseBody
    boolean addProduct(ModelMap model, @RequestParam(value = "articule", required = true) String articule,
                             @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "stock", required = true) String stock,
                             @RequestParam(value = "price", required = true) String price,
                             @RequestParam(value = "currency", required = true) String currency,
                             @RequestParam(value = "groupId", required = true) String groupId) {

        Product product = new Product();
        product.setName(name);
        product.setArticule(articule);
        product.setRetailPrice(Float.parseFloat(price));
        product.setWholesalePrice(Float.parseFloat(price));
        product.setStock(Integer.parseInt(stock));
        product.setCurrency(currency);
        product.setProductGroup(groupService.findById(Long.parseLong(groupId)));

        productService.create(product);

        return true;
    }

    @RequestMapping(value = "/products/remove", method = RequestMethod.POST)
    public @ResponseBody
    boolean removeProduct(ModelMap model, @RequestParam(value = "articule", required = true) String articule) {
        System.out.println(articule);
        productService.delete(productService.findByArticule(articule).getId());

        return true;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    User signin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        User user = userService.findByUsername(username);
        UserRole userRole = new UserRole();
        if (user != null) userRole = userRoleService.findByUserId(user.getId());
        if (user != null) {
            BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(12);
            bcryptEncoder.encode(password);

            if (bcryptEncoder.matches(password, user.getPassword()) && userRole.getAuthority().equals("ROLE_ADMIN")){
                return user;
            }
        }

        return null;

    }


}