package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Order;
import com.example.persistencelayer.dto.OrderEmployeeNameDto;
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

    @Query("select new com.example.persistencelayer.dto.OrderEmployeeNameDto(o.creationDate, o.creationDate, concat(o.employee.name,' ',o.employee.surname)) from orders o join o.employee where o.orderId<= :limit " )
    List<OrderEmployeeNameDto> findOrderDtoLimit(@Param("limit") long limit);

    @Query("select o from orders o join fetch o.employee where o.orderId <= :limit")
    List<Order> findOrdersFetchEmployees(@Param("limit") long limit);

    List<Order> findAllByCreationDateBefore(LocalDateTime creationDate);

    @Query(nativeQuery = true, value = "SELECT * from orders LIMIT :limit")
    List<Order> findMultipleTop(@Param("limit") int limit);

//
//    List<Order> findMultipleTopFetchProducts(@Param("limit") int limit);

    @Query("select o from orders o join fetch o.products where o.orderId <= :limit")
    List<Order> findMultipleTopFetchProducts(@Param("limit") long limit);

    void deleteByCreationDateBefore(LocalDateTime localDateTime);
}
