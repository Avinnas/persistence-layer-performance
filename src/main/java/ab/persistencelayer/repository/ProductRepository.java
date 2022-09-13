package ab.persistencelayer.repository;

import ab.persistencelayer.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(nativeQuery = true, value = "SELECT * from products LIMIT :limit")
    List<Product> findMultipleTop(@Param("limit") long limit);


    @Cacheable("products")
    @Query(nativeQuery = true, value = "SELECT * from products LIMIT :limit")
    List<Product> findMultipleTopWithCaching(@Param("limit") long limit);

    @Query("select p from products p where p.productId in :ids")
    List<Product> findByIds(@Param("ids") List<Long> idList);

    @Query("select p from products p join fetch p.orders where p.productId=:id")
    Optional<Product> findByIdFetchOrders(@Param("id") long id);
}
