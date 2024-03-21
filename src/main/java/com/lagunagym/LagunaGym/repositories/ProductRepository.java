package com.lagunagym.LagunaGym.repositories;

import com.lagunagym.LagunaGym.models.Person;
import com.lagunagym.LagunaGym.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product,Long> {
}
