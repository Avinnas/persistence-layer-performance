package com.example.persistencelayer.repository;

import com.example.persistencelayer.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p from products p join fetch p.image")
    public List<Product> findAllWithFetchedImages();

    @Query("SELECT p from products p")
    @Cacheable("products")
    List<Product> findAllWithCaching();
}
