package com.lagunagym.LagunaGym.models.specifications;

import com.lagunagym.LagunaGym.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<Product> containsWord(String substr){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + substr +"%"));
    }
    public static Specification<Product> priceLessThan(Double maxprice){
        return ((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxprice));
    }
    public static Specification<Product> priceGreaterThan(Double minprice){
        return ((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minprice));
    }
}
