package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public void deleteAllByName(String name);

    public List<Customer> findBySurnameContaining(String substring);

    public List<Customer> findBySurname(String surname);
    public void deleteBySurnameContaining(String substring);

    @Modifying
    @Query("DELETE from customers where surname LIKE %:substring%")
    public void deleteBySurnameContainingUsingQuery(@Param("substring") String substring);
}
