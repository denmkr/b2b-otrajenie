package ru.dm.shop.controller;

/**
 * Created by Denis on 05.05.16.
 */

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dm.shop.entity.Product;
import ru.dm.shop.entity.User;
import ru.dm.shop.service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRoleService userRoleService;

    Authentication authentication;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String admin(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users_count", userService.countOfUsers());
            model.addAttribute("orders_count", orderService.countOfOrdersToday());
            return "admin";
        }
    }

    /* Страница заказов */

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("orders", orderService.findAll());

            return "admin/orders";
        }
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String ordersAjax(ModelMap model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/ajax/orders_content";
    }


    /* Страница пользователей */

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users", userService.findAll());
            return "admin/users";
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String usersAjax(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "admin/ajax/users_content";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String addUsersPage(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            return "admin/users_add";
        }
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUsersPageAjax(ModelMap model) {
        return "admin/ajax/users_add_content";
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    public String addUser(ModelMap model,
                          @RequestParam(value = "name", required = true) String name,
                          @RequestParam(value = "password", required = true) String password,
                          @RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "userRole", required = true) String role) {
        User user = new User();
        user.setUsername(StringEscapeUtils.escapeHtml4(name));
        user.setEmail(StringEscapeUtils.escapeHtml4(email));
        user.setPassword(StringEscapeUtils.escapeHtml4(password));

        userService.create(user);

        if (role.equals("ROLE_ADMIN")) userRoleService.createAdmin(user);
        if (role.equals("ROLE_PARTNER")) userRoleService.createPartner(user);
        if (role.equals("ROLE_ADMIN")) userRoleService.createUser(user);

        model.addAttribute("message", "Пользователь добавлен");
        model.addAttribute("href", "/users/add");
        return "admin/notif";
    }

    @RequestMapping(value = "/users/remove", method = RequestMethod.GET)
    public String removeUsersPage(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users", userService.findAll());
            return "admin/users_remove";
        }
    }

    @RequestMapping(value = "/users/remove", method = RequestMethod.POST)
    public String removeUsersPageAjax(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "admin/ajax/users_remove_content";
    }

    @RequestMapping(value = "/users/removeUser", method = RequestMethod.POST)
    public String removeUser(ModelMap model, @RequestParam(value = "userId", required = true) String userId) {
        userRoleService.deleteByUserId(Long.parseLong(userId));
        userService.delete(Long.parseLong(userId));

        model.addAttribute("message", "Пользователь удален");
        model.addAttribute("href", "/users/remove");
        return "admin/notif";
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.GET)
    public String updateUsersPage(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users", userService.findAll());

            return "admin/users_change";
        }
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUsersPageAjax(ModelMap model) {
        model.addAttribute("users", userService.findAll());

        return "admin/ajax/users_change_content";
    }

    @RequestMapping(value = "/users/updateUser", method = RequestMethod.POST)
    public String updateUser(ModelMap model, @RequestParam(value = "userId", required = true) String userId,
                             @RequestParam(value = "userRole", required = true) String userRole) {

        userRoleService.changeUserRole(userRole, Long.parseLong(userId));

        model.addAttribute("message", "Роль пользователя изменена");
        model.addAttribute("href", "/users/update");
        return "admin/notif";
    }


    /* Страница товаров */

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("products", productService.findAll());
            return "admin/products";
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String productsAjax(ModelMap model) {
        model.addAttribute("products", productService.findAll());
        return "admin/ajax/products_content";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String addProductsPage(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("groups", groupService.findAll());
            return "admin/products_add";
        }
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String addProductsAjaxPage(ModelMap model) {
        model.addAttribute("groups", groupService.findAll());
        return "admin/ajax/products_add_content";
    }

    @RequestMapping(value = "/products/addProduct", method = RequestMethod.POST)
    public String addProduct(ModelMap model, @RequestParam(value = "articule", required = true) String articule,
                             @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "stock", required = true) String stock,
                             @RequestParam(value = "price", required = true) String price,
                             @RequestParam(value = "currency", required = true) String currency,
                             @RequestParam(value = "groupId", required = true) String groupId) {

        Product product = new Product();
        product.setName(name);
        product.setArticule(articule);
        product.setRetailPrice(new BigDecimal(price).setScale(10));
        product.setWholesalePrice(new BigDecimal(price).setScale(10));
        product.setStock(Integer.parseInt(stock));
        product.setCurrency(currency);

        product.setProductGroup(groupService.findById(Long.parseLong(groupId)));

        productService.create(product);

        model.addAttribute("message", "Товар добавлен");
        model.addAttribute("href", "/products/add");
        return "admin/notif";
    }

    @RequestMapping(value = "/products/remove", method = RequestMethod.GET)
    public String removeProductPage(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            return "admin/products_remove";
        }
    }

    @RequestMapping(value = "/products/remove", method = RequestMethod.POST)
    public String removeProductPageAjax(ModelMap model) {
        return "admin/ajax/products_remove_content";
    }

    @RequestMapping(value = "/products/removeProduct", method = RequestMethod.POST)
    public String removeProduct(ModelMap model, @RequestParam(value = "articule", required = true) String articule) {
        productService.delete(productService.findByArticule(articule).getId());

        model.addAttribute("message", "Товар удален");
        model.addAttribute("href", "/products/remove");
        return "admin/notif";
    }

    @RequestMapping(value = "/products/upload", method = RequestMethod.GET)
    public String updateProduct(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            return "admin/products_upload";
        }
    }

    @RequestMapping(value = "/products/upload", method = RequestMethod.POST)
    public String updateProductAjax(ModelMap model) {

        return "admin/ajax/products_upload_content";
    }


    /* Страница информации */

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String information(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users_count", userService.countOfUsers());
            model.addAttribute("orders_count", orderService.countOfOrdersToday());
            return "admin/information";
        }
    }

    @RequestMapping(value = "/information", method = RequestMethod.POST)
    public String informationAjax(ModelMap model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/403";
        } else {
            model.addAttribute("users_count", userService.countOfUsers());
            model.addAttribute("orders_count", orderService.countOfOrdersToday());

            return "admin/ajax/information_content";
        }
    }

   /* @RequestMapping(value = "/information/json", method = RequestMethod.GET)
    public @ResponseBody
    List<Long> json(ModelMap model) {
        return orderService.getOrdersCounts();
    }
    */
}



