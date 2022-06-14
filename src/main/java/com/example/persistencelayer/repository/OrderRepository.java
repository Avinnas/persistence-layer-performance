package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Order;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from orders o join fetch o.products")
    public List<Order> findAllFetchProducts();

    @Query("select o from orders o join fetch o.products where o.employee.PersonId = :id")
    public List<Order> findByEmployeeIdWithFetch(@Param("id") long id);

    @Query("select o from orders o where o.employee.PersonId = :id")
    public List<Order> findByEmployeeId(@Param("id") long id);


    @Query("select o from orders o")
    @Cacheable("orders")
    public List<Order> findAllWithCache();

    @Query("select o from orders o join fetch o.products")
    @Cacheable("orders")
    public List<Order> findAllFetchProductsWithCache();
}
