package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Customer;
import com.example.persistencelayer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query(nativeQuery = true, value = "SELECT * from employees LIMIT :limit")
    List<Employee> findMultipleTop(@Param("limit") long limit);
}
