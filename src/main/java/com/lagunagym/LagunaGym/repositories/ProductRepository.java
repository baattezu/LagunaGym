package com.lagunagym.LagunaGym.repositories;

import com.lagunagym.LagunaGym.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product>, CrudRepository<Product,Long>, JpaRepository<Product,Long> {
    @Query(value = "SELECT MAX(p.id) FROM Product p")
    Long findMaxUid(); // blya ya tupoi


    @Query(value = "SELECT p FROM Product p order by p.views desc limit 3")
    List<Product> top3();


    List<Product> findTop3ByOrderByViewsDesc();
}