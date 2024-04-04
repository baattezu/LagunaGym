package com.lagunagym.LagunaGym.repositories;

import com.lagunagym.LagunaGym.models.Person;
import com.lagunagym.LagunaGym.models.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product>, CrudRepository<Product,Long> {
}
