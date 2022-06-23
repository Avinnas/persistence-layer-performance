package com.example.persistencelayer.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity(name = "customers")
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.example.persistencelayer.model.Customer")
public class Customer extends Person implements Serializable {


    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    @Cascade(CascadeType.DELETE)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Order> orders;

    public Customer() {
    }

    public Customer(String name, String surname, List<Order> orders) {
        super(name, surname);
        this.orders = orders;
    }
}
