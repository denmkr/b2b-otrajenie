package ru.dm.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.Product;
import ru.dm.shop.entity.ProductGroup;
import ru.dm.shop.repository.ProductRepository;
import ru.dm.shop.service.GroupService;
import ru.dm.shop.service.ProductService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    public ProductRepository productRepository;
    @Autowired
    public GroupService groupService;

    @Override
    @Transactional
    public Product create(Product product) {
        Product createdProduct = product;
        return productRepository.save(createdProduct);
    }

    @Override
    @Transactional
    public Product findById(long id) {
        return productRepository.findOne(id);
    }

    @Override
    @Transactional
    public Product findByArticule(String articule) {
        return productRepository.findByArticule(articule);
    }

    @Override
    @Transactional
    public boolean addProducts(List<Product> products) {
        for (Product product : products) {
            product.setProductGroup(groupService.findByGroupId(product.getProductGroup().getGroupId()));
            productRepository.save(product);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateProducts(List<Product> products) {
        for (Product product : products) {
            product.setProductGroup(groupService.findByGroupId(product.getProductGroup().getGroupId()));

            if (productRepository.findByArticule(product.getArticule()) == null) {
                productRepository.save(product);
            }
            else productRepository.updateProductByArticule(product.getArticule(), product.getName(), product.getStock(), product.getRetailPrice(), product.getWholesalePrice(), product.getCurrency(), product.getProductGroup());
        }

        return true;
    }

    @Override
    public boolean deleteMissing(List<Product> products) {

        List<Product> productListFromDB = productRepository.findAll();

        for (Product product: productListFromDB) {
            product.setId(null);
        }
        for (Product product: products) {
            product.setId(null);
        }

        productListFromDB.removeAll(products);

        for (Product product : productListFromDB) {
            productRepository.deleteProductByArticule(product.getArticule());
        }

        return true;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Product deletedProduct = productRepository.findOne(id);
        productRepository.delete(deletedProduct);
        return true;
    }



    @Override
    @Transactional
    public Page<Product> findAll(String groupId, int page, String stock, String searchProduct, String searchArticule, String sort, int count) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        int stockValue;
        if (stock.equals("off")) stockValue = -1;
        else stockValue = 0;

        String[] parts = sort.split("_");
        sort = parts[0];
        String direction = parts[1];

        Sort.Direction sortDirection;
        if (direction.equals("ASC")) sortDirection = Sort.Direction.ASC;
        else sortDirection = Sort.Direction.DESC;

        PageRequest pageRequest = new PageRequest(page - 1, count, sortDirection, sort);

        Page<Product> productPage;

        if (!groupId.isEmpty()) {
            Collection<ProductGroup> groups = new ArrayList<ProductGroup>();
            groups.add(groupService.findByGroupId(groupId));
            for (ProductGroup group : groupService.findByGroupId(groupId).getChildGroups()) {
                groups.add(group);
                for (ProductGroup group1 : group.getChildGroups()) {
                    groups.add(group1);
                    for (ProductGroup group2 : group1.getChildGroups()) {
                        groups.add(group2);
                    }
                }
            }

            productPage = productRepository.findByProductGroupInAndStockGreaterThanAndNameContainingIgnoreCaseOrProductGroupInAndStockGreaterThanAndArticuleContainingIgnoreCase(groups, stockValue, searchProduct, groups, stockValue, searchArticule, pageRequest);
        }
        else {
            productPage = productRepository.findByStockGreaterThanAndNameContainingIgnoreCaseOrStockGreaterThanAndArticuleContainingIgnoreCase(stockValue, searchProduct, stockValue, searchArticule, pageRequest);
        }

        /*
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PARTNER"))) {
        }*/

        return productPage;
    }

    @Override
    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product update(Product product) {
        Product updatedProduct = productRepository.findOne(product.getId());

        updatedProduct.setName(product.getName());
        return updatedProduct;
    }




}