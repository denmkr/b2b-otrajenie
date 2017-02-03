package ru.dm.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.Product;
import ru.dm.shop.entity.ProductGroup;

import java.util.Collection;
import java.util.List;

/**
 * Created by Denis on 14.04.2016.
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
    List<Product> findAll();

    Product findByArticule(String articule);

    @Modifying
    @Transactional
    @Query("Delete Product product where product.articule = ?1")
    void deleteProductByArticule(String articule);

    @Modifying
    @Transactional
    @Query("UPDATE Product product SET product.name = ?2, product.stock = ?3, product.retailPrice = ?4, product.wholesalePrice = ?5, product.currency = ?6, product.productGroup = ?7 where product.articule = ?1")
    void updateProductByArticule(String articule, String name, int stock, float retailPrice, float wholesalePrice, String currency, ProductGroup productGroup);

    Page<Product> findByProductGroupInAndStockGreaterThanAndNameContainingIgnoreCaseOrProductGroupInAndStockGreaterThanAndArticuleContainingIgnoreCase(Collection<ProductGroup> groups, int num, String name, Collection<ProductGroup> groups2, int num2, String articule, Pageable pageable);
    Page<Product> findByStockGreaterThanAndNameContainingIgnoreCase(int num, String name, Pageable pageable);
    Page<Product> findByStockGreaterThanAndNameContainingIgnoreCaseOrStockGreaterThanAndArticuleContainingIgnoreCase(int num, String name, int num2, String articule, Pageable pageable);

}
