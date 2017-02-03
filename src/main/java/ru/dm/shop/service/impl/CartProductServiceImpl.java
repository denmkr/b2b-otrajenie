package ru.dm.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.Cart;
import ru.dm.shop.entity.CartProduct;
import ru.dm.shop.entity.Product;
import ru.dm.shop.repository.CartProductRepository;
import ru.dm.shop.service.CartProductService;
import ru.dm.shop.service.ProductService;
import ru.dm.shop.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {

    @Resource
    public CartProductRepository cartProductRepository;
    @Autowired
    public UserService userService;
    @Autowired
    public ProductService productService;

    @Override
    @Transactional
    public boolean addProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CartProduct cartProduct;
        cartProduct = cartProductRepository.findByUserIdAndProductId(userService.findByUsername(authentication.getName()).getId(), productService.findByArticule(product.getArticule()).getId());

        if (cartProduct != null) {
            int count = cartProduct.getCount();
            product = productService.findByArticule(product.getArticule());
            cartProductRepository.updateProductInCart(count + 1, userService.findByUsername(authentication.getName()).getId(), product);
        }
        else {
            cartProduct = new CartProduct();
            cartProduct.setProduct(productService.findByArticule(product.getArticule()));
            cartProduct.setUserId(userService.findByUsername(authentication.getName()).getId());
            cartProduct.setCount(1);
            cartProductRepository.saveAndFlush(cartProduct);
        }

        return true;

    }

    @Override
    public boolean setProductAmount(Product product, int amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CartProduct cartProduct;
        cartProduct = cartProductRepository.findByUserIdAndProductId(userService.findByUsername(authentication.getName()).getId(), productService.findByArticule(product.getArticule()).getId());

        int count = cartProduct.getCount();
        product = productService.findByArticule(product.getArticule());
        cartProductRepository.updateProductInCart(amount, userService.findByUsername(authentication.getName()).getId(), product);

        return true;
    }

    @Override
    @Transactional
    public Cart getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PageRequest pageRequest = new PageRequest(0, 100, Sort.Direction.ASC, "product");
        Page<CartProduct> cartProducts = cartProductRepository.findByUserId(userService.findByUsername(authentication.getName()).getId(), pageRequest);

        Cart cart = new Cart();
        cart.setProducts(cartProducts.getContent());

        return cart;
    }

    @Override
    @Transactional
    public boolean removeProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CartProduct cartProduct = cartProductRepository.findByUserIdAndProductId(userService.findByUsername(authentication.getName()).getId(), productService.findByArticule(product.getArticule()).getId());

        int count = cartProduct.getCount();
        if (count > 1) {
            product = productService.findByArticule(product.getArticule());
            cartProductRepository.updateProductInCart(count - 1, userService.findByUsername(authentication.getName()).getId(), product);
        }
        else cartProductRepository.delete(cartProduct);

        return true;
    }

    @Override
    public boolean removeProducts(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CartProduct cartProduct = cartProductRepository.findByUserIdAndProductId(userService.findByUsername(authentication.getName()).getId(), productService.findByArticule(product.getArticule()).getId());

        cartProductRepository.delete(cartProduct);

        return true;
    }

    @Override
    public boolean removeAllProducts() {
        cartProductRepository.deleteAll();

        return true;
    }

    @Override
    public boolean removeCart(Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        cartProductRepository.deleteProductInCart(userService.findByUsername(authentication.getName()).getId());

        return true;
    }

}