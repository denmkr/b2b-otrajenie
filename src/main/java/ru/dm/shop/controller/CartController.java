package ru.dm.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.Num;
import ru.dm.shop.entity.Product;
import ru.dm.shop.service.CartProductService;
import ru.dm.shop.service.ProductService;

import javax.servlet.http.HttpSession;

/**
 * Created by Denis on 22/03/2016.
 */

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartProductService cartProductService;
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String cart(ModelMap model, HttpSession session, @RequestParam(value = "ajax", required = false, defaultValue = "0") String ajax) {

        Cart cart;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());
            cart = (Cart) session.getAttribute("cart");
        }
        else cart = cartProductService.getCart();

        model.addAttribute("cart", cart);

        if (ajax.equals("1")) return "ajax/cart_content";
        else return "cart";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addToCart(ModelMap model, @RequestParam(value = "articule", required = true) String articule, @RequestParam(value = "amount", required = true) Integer amount, HttpSession session) {

        Product product = new Product();
        product.setArticule(articule);
        Cart cart;

        if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            Product product1 = productService.findByArticule(articule);

            for (int i=1; i<=amount; i++) {
                cart.addProduct(product1);
            }

            session.setAttribute("cart", cart);
        }
        else {
            for (int i=1; i<=amount; i++) {
                cartProductService.addProduct(product);
            }

            cart = cartProductService.getCart();
        }

        Num size = new Num();
        size.setNum(cart.getSize());

        model.addAttribute("cart_size", size);
        return "ajax/cart_size";
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public String setAmountToCart(ModelMap model, @RequestParam(value = "articule", required = true) String articule, @RequestParam(value = "amount", required = true) Integer amount, HttpSession session) {

        Product product = new Product();
        product.setArticule(articule);
        Cart cart;

        if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            Product product1 = productService.findByArticule(articule);

            cart.setProductCount(product1, amount);

            session.setAttribute("cart", cart);
        }
        else {
            cartProductService.setProductAmount(product, amount);
            cart = cartProductService.getCart();
        }

        Num size = new Num();
        size.setNum(cart.getSize());

        model.addAttribute("cart_size", size);
        return "ajax/cart_size";
    }


    @RequestMapping(value = "/getprice", method = RequestMethod.GET)
    public String getPrice(ModelMap model, HttpSession session) {

        Cart cart;

        if (session.getAttribute("cart") == null) session.setAttribute("cart", new Cart());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            session.setAttribute("cart", cart);
        }
        else {
            cart = cartProductService.getCart();
        }

        Num price = new Num();
        price.setfNum(cart.getPrice());

        model.addAttribute("cart_price", price);
        return "ajax/cart_price";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeFromCart(ModelMap model, @RequestParam(value = "articule", required = true) String articule, HttpSession session) {

        Product product = new Product();
        product.setArticule(articule);
        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            Product product1 = productService.findByArticule(articule);
            cart.removeProducts(product1);
            session.setAttribute("cart", cart);
        }
        else {
            cartProductService.removeProducts(product);
            cart = cartProductService.getCart();
        }

        Num size = new Num();
        size.setNum(cart.getSize());

        model.addAttribute("cart_size", size);
        return "ajax/cart_size";
    }

    @RequestMapping(value = "/removeone", method = RequestMethod.POST)
    public String removeOneFromCart(ModelMap model, @RequestParam(value = "articule", required = true) String articule, HttpSession session) {

        Product product = new Product();
        product.setArticule(articule);
        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            Product product1 = productService.findByArticule(articule);
            cart.removeProduct(product1);
            session.setAttribute("cart", cart);
        }
        else {
            cartProductService.removeProduct(product);
            cart = cartProductService.getCart();
        }

        Num size = new Num();
        size.setNum(cart.getSize());

        model.addAttribute("cart_size", size);
        return "ajax/cart_size";
    }

    @RequestMapping(value = "/removeall", method = RequestMethod.POST)
    public String removeAllFromCart(ModelMap model, HttpSession session) {

        Cart cart;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            cart = (Cart) session.getAttribute("cart");
            cart.removeAllProducts();
            session.setAttribute("cart", cart);
        }
        else {
            cartProductService.removeAllProducts();
            cart = cartProductService.getCart();
        }

        Num size = new Num();
        size.setNum(cart.getSize());

        model.addAttribute("cart_size", size);
        return "ajax/cart_size";
    }


}
