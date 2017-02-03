package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.ProductGroup;

import java.util.List;

/**
 * Created by Denis on 14.04.2016.
 */

@Repository
public interface GroupRepository extends JpaRepository<ProductGroup, Long> {

    List<ProductGroup> findAll();
    ProductGroup findByGroupId(String groupId);

    List<ProductGroup> findAllByParentGroup(String parentGroup);

    @Modifying
    @Transactional
    @Query("UPDATE ProductGroup productGroup SET productGroup.name = ?2 where productGroup.groupId = ?1")
    void updateGroupByGroupId(String groupId, String name);

    @Modifying
    @Transactional
    @Query("UPDATE ProductGroup productGroup SET productGroup.parentGroup = ?2 where productGroup.id = ?1")
    void addParentGroupByGroupId(long id, ProductGroup parentGroup);

}
