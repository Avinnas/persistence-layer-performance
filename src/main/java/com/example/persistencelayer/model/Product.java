package com.example.persistencelayer.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

@Entity(name = "products")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="prod_sequence")
    @SequenceGenerator(name="prod_sequence", sequenceName = "prod_id_seq", allocationSize = 100)
    private long productId;
    private String name;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    private ProductCategory productCategory;

    @ManyToMany(mappedBy = "products")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<Order> orders;

    public Product() {
    }

    public Product(String name, int quantity, Image image, ProductCategory productCategory, List<Order> orders) {
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.productCategory = productCategory;
        this.orders = orders;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
