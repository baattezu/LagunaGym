package org.example.productservice.services;



import jakarta.transaction.Transactional;
import org.example.productservice.entities.Product;
import org.example.productservice.entities.specifications.ProductSpecs;
import org.example.productservice.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product){
        product.setId(productRepository.findMaxUid()+1);
        product.setViews(0);
        productRepository.save(product);
    }
    public void removeProduct(Long id){
        productRepository.deleteById(id);
    }
    public void updateProduct(Long id, String title, String description, Double price){
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        productRepository.save(product);
    }
    @Transactional
    public Product getProduct(String id){
        Product product = productRepository.findById(Long.parseLong(id)).orElseThrow();
        product.setViews(product.getViews()+1);
        productRepository.save(product);
        return product;
    }
//    public List<Product> getProductListWithFiltersAndPagination(String substr, Double minPrice, Double maxPrice, Pageable pageable){
//        Specification<Product> filters = Specification.where(null);
//        if (substr != null) {
//            filters = filters.and(ProductSpecs.containsWord(substr));
//        }
//        if (minPrice != null) {
//            filters = filters.and(ProductSpecs.priceGreaterThan(minPrice));
//        }
//        if (maxPrice != null) {
//            filters = filters.and(ProductSpecs.priceLessThan(maxPrice));
//        }
//        return productRepository.findAll(filters, pageable).getContent();
//    }

    public List<Product> getProductListWithPagination(Pageable pageable){
        return productRepository.findAll(pageable).getContent();
    }
    public void updateViews(Product product){
        product.setViews(product.getViews()+1);
    }

    public List<Product> getTop3() {
        return productRepository.findTop3ByOrderByViewsDesc();
    }
}
