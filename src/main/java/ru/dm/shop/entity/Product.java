package ru.dm.shop.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by denis on 18/06/2017.
 */
@Entity
@Table(name = "products", schema = "public", catalog = "otrajenie")
public class Product {
    private Long id;
    private String articule;
    private String name;
    private Integer stock;
    private BigDecimal retailPrice;
    private String currency;
    private BigDecimal wholesalePrice;

    public Product() {
    }

    public Product(String articule, String name, Integer stock, BigDecimal retailPrice, String currency, BigDecimal wholesalePrice, ProductGroup group) {
        this.articule = articule;
        this.name = name;
        this.stock = stock;
        this.retailPrice = retailPrice;
        this.currency = currency;
        this.wholesalePrice = wholesalePrice;
        this.group = group;
    }

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
    @Column(name = "articule")
    public String getArticule() {
        return articule;
    }

    public void setArticule(String articule) {
        this.articule = articule;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "stock")
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "retail_price", precision = 19, scale = 7, columnDefinition="NUMBER(19,7)")
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Basic
    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "wholesale_price", precision = 19, scale = 7, columnDefinition="NUMBER(19,7)")
    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    /* */
    private ProductGroup group;

    @ManyToOne(targetEntity = ProductGroup.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    public ProductGroup getProductGroup() {
        return group;
    }
    public void setProductGroup(ProductGroup group) {
        this.group = group;
    }
    /* */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product that = (Product) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (articule != null ? !articule.equals(that.articule) : that.articule != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (stock != null ? !stock.equals(that.stock) : that.stock != null) return false;
        if (retailPrice != null ? !retailPrice.equals(that.retailPrice) : that.retailPrice != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (wholesalePrice != null ? !wholesalePrice.equals(that.wholesalePrice) : that.wholesalePrice != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (articule != null ? articule.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (retailPrice != null ? retailPrice.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (wholesalePrice != null ? wholesalePrice.hashCode() : 0);
        return result;
    }
}
