package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.model.OrderDto;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from orders o join fetch o.products")
    public List<Order> findAllFetchProducts();

    @Query("select o from orders o join fetch o.products where o.employee.personId = :id")
    public List<Order> findByEmployeeIdWithFetch(@Param("id") long id);

    @Query("select o from orders o where o.employee.personId = :id")
    public List<Order> findByEmployeeId(@Param("id") long id);


    @Query("select o from orders o")
    @Cacheable("orders")
    public List<Order> findAllWithCache();

    @Query("select o from orders o join fetch o.products")
    @Cacheable("orders")
    public List<Order> findAllFetchProductsWithCache();

    @Query("select new com.example.persistencelayer.model.OrderDto(o.creationDate, o.deliveryDate) from orders o where o.creationDate<'2022-01-01 00:00:00' " )
    public List<OrderDto> findAllOrderDtoByCreationDateLessThan(LocalDateTime creationDate);

    List<Order> findAllByCreationDateBefore(LocalDateTime creationDate);

    @Query(nativeQuery = true, value = "SELECT * from orders LIMIT :limit")
    List<Order> findMultipleTop(@Param("limit") int limit);

    void deleteByCreationDateBefore(LocalDateTime localDateTime);
}
