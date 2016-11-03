package com.acme.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = {
                    @JoinColumn(name = "productId", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "categoryId", nullable = false, updatable = false)
            }
    )
    private List<Category> categories = new ArrayList<>(0);

    protected Product() {
    }

    public Product(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Category> getCategogies() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}