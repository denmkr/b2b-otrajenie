package ru.dm.shop.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 06.05.16.
 */
public class Cart {

    private List<CartProduct> cartProducts;

    public Cart() {
        cartProducts = new ArrayList<CartProduct>();
    }

    public Cart(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public int getSize() {
        int size = 0;

        for (CartProduct cartProduct : cartProducts) {
            size += cartProduct.getCount();
        }

        return size;
    }

    public Float getPrice() {
        Float price = new Float(0);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority: authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_PARTNER")) {
                for (CartProduct cartProduct : cartProducts) {
                    price += cartProduct.getProduct().getWholesalePrice() * cartProduct.getCount();
                }
            }
            else {
                for (CartProduct cartProduct : cartProducts) {
                    price += cartProduct.getProduct().getRetailPrice() * cartProduct.getCount();
                }
            }
        }

        return price;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public Long getSizeByArticule(String articule) {
        Long size = 0L;

        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getArticule().equals(articule)) {
                size = cartProduct.getCount();
                return size;
            }

        }

        return size;
    }

    public void setProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void setProductCount(Product product, int count) {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().equals(product)) {
                cartProduct.setCount(count);
                return;
            }
        }
    }

    public void addProduct(Product product) {

        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().equals(product)) {
                cartProduct.setCount(cartProduct.getCount() + 1);
                return;
            }
        }

        CartProduct newCartProduct = new CartProduct();
        newCartProduct.setProduct(product);
        newCartProduct.setCount(1);

        this.cartProducts.add(newCartProduct);
    }

    public void removeProduct(Product product) {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().equals(product) && !cartProduct.getCount().equals(1)) {
                cartProduct.setCount(cartProduct.getCount() - 1);
                return;
            }
        }

        CartProduct removedCartProduct = new CartProduct();
        removedCartProduct.setProduct(product);
        removedCartProduct.setCount(1);

        cartProducts.remove(removedCartProduct);
    }

    public void removeProducts(Product product) {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().equals(product)) {
                cartProducts.remove(cartProduct);
                return;
            }
        }
    }

    public void removeAllProducts() {
        cartProducts.removeAll(cartProducts);
    }

}
