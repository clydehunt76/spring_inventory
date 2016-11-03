package com.acme.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<Product> products = new ArrayList<>(0);

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, name='%s']",
                id, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return this.products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}