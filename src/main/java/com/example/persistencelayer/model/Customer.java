package com.example.persistencelayer.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "customers")
public class Customer extends Person{

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer() {
    }

    public Customer(String name, String surname, List<Order> orders) {
        super(name, surname);
        this.orders = orders;
    }
}
