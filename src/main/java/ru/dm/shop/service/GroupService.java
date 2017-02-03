package ru.dm.shop.service;


import ru.dm.shop.entity.GroupRelation;
import ru.dm.shop.entity.ProductGroup;

import java.util.List;

/**
 * Created by Denis on 17.04.16.
 */
public interface GroupService {
    ProductGroup create(ProductGroup productGroup);
    boolean delete(long id);
    List<ProductGroup> findAll();
    ProductGroup update(ProductGroup productGroup);
    ProductGroup findById(long id);
    List<ProductGroup> findAllParents();

    boolean addGroups(List<ProductGroup> productGroups);
    boolean updateGroups(List<ProductGroup> productGroups);
    boolean addParentsGroupsToGroups(List<GroupRelation> relations);
    ProductGroup findByGroupId(String groupId);
}
