package com.example.persistencelayer.model;

import javax.persistence.*;
//
@MappedSuperclass
//@Entity(name = "persons")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="emp_sequence")
//    @SequenceGenerator(name="emp_sequence", sequenceName = "emp_id_seq", allocationSize = 100)

    @GeneratedValue(strategy = GenerationType.AUTO)

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personId;
    private String name;
    private String surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person() {
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
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
