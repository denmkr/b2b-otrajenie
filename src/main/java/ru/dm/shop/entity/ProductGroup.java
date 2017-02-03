package ru.dm.shop.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 12.05.16.
 */
@Entity
@Table(name = "product_groups", schema = "public", catalog = "mvc")
public class ProductGroup {
    private Long id;
    private String groupId;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private ProductGroup parentGroup;

    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="parent_group_id")
    public ProductGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(ProductGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    private List<ProductGroup> childGroups = new ArrayList<ProductGroup>();

    @OneToMany(mappedBy="parentGroup", fetch = FetchType.EAGER)
    public List<ProductGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(List<ProductGroup> childGroups) {
        this.childGroups = childGroups;
    }

    /*
    private List<Product> products;

    @OneToMany(targetEntity = Product.class, mappedBy = "productGroup", fetch = FetchType.EAGER)
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductGroup that = (ProductGroup) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
