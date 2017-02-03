package ru.dm.shop.service;

import org.springframework.data.domain.Page;
import ru.dm.shop.entity.Product;

import java.util.List;

/**
 * Created by Denis on 17.04.16.
 */
public interface ProductService {
    Product create(Product product);
    boolean delete(long id);
    Product update(Product product);
    Product findById(long id);
    Product findByArticule(String articule);
    boolean addProducts(List<Product> products);
    boolean updateProducts(List<Product> products);

    boolean deleteMissing(List<Product> products);

    Page<Product> findAll(String groupId, int page, String stock, String searchProduct, String searchArticule, String sort, int count);
    List<Product> findAll();
}
