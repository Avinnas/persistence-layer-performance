package com.example.persistencelayer.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="emp_sequence")
    @SequenceGenerator(name="emp_sequence", sequenceName = "emp_id_seq", allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PersonId;
    private String name;
    private String surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person() {
    }

    public long getPersonId() {
        return PersonId;
    }

    public void setPersonId(long personId) {
        PersonId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
