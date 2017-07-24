package ru.dm.shop.service;


import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.Product;

/**
 * Created by Denis on 17.04.16.
 */
public interface CartProductService {
    boolean addProduct(Product product);
    boolean setProductAmount(Product product, Integer amount);
    Cart getCart();
    boolean removeProduct(Product product);
    boolean removeProducts(Product product);
    boolean removeAllProducts();
    boolean removeCart(Cart cart);
}

