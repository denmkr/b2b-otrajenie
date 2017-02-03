package ru.dm.shop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dm.shop.entity.GroupRelation;
import ru.dm.shop.entity.ProductGroup;
import ru.dm.shop.repository.GroupRepository;
import ru.dm.shop.service.GroupService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    public GroupRepository groupRepository;

    @Override
    @Transactional
    public ProductGroup create(ProductGroup productGroup) {
        ProductGroup createdProductGroup = productGroup;
        return groupRepository.saveAndFlush(createdProductGroup);
    }

    @Override
    @Transactional
    public ProductGroup findById(long id) {
        return groupRepository.findOne(id);
    }

    @Override
    @Transactional
    public boolean addGroups(List<ProductGroup> productGroups) {
        for (ProductGroup productGroup : productGroups) {
            groupRepository.saveAndFlush(productGroup);
        }
        return true;
    }

    @Override
    public boolean updateGroups(List<ProductGroup> productGroups) {
        for (ProductGroup group : productGroups) {
            if (groupRepository.findByGroupId(group.getGroupId()) == null) {
                groupRepository.saveAndFlush(group);
            }
            else groupRepository.updateGroupByGroupId(group.getGroupId(), group.getName());
        }

        return true;
    }

    @Override
    public boolean addParentsGroupsToGroups(List<GroupRelation> relations) {
        for (GroupRelation groupRelation : relations) {
            groupRepository.addParentGroupByGroupId(groupRepository.findByGroupId(groupRelation.getChildGroupId()).getId(), groupRepository.findByGroupId(groupRelation.getGroupId()));
        }
        return true;
    }


    @Override
    public ProductGroup findByGroupId(String groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        ProductGroup deletedProductGroup = groupRepository.findOne(id);
        groupRepository.delete(deletedProductGroup);
        return true;
    }

    @Override
    @Transactional
    public List<ProductGroup> findAll() {
        List<ProductGroup> products = groupRepository.findAll();
        return products;
    }

    @Override
    @Transactional
    public List<ProductGroup> findAllParents() {
        List<ProductGroup> products = groupRepository.findAllByParentGroup(null);
        return products;
    }

    @Override
    @Transactional
    public ProductGroup update(ProductGroup productGroup) {
        ProductGroup updatedProductGroup = groupRepository.findOne(productGroup.getId());

        updatedProductGroup.setName(productGroup.getName());
        return updatedProductGroup;
    }

}