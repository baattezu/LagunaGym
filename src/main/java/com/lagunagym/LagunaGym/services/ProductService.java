package com.lagunagym.LagunaGym.services;

import com.lagunagym.LagunaGym.models.Product;
import com.lagunagym.LagunaGym.models.specifications.ProductSpecs;
import com.lagunagym.LagunaGym.repositories.ProductRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product createProductForForm(){
        Long lastId = 0L;
        for( Product ps : productRepository.findAll()){
            lastId = ps.getId();
        }
        return new Product(lastId+1);
    }
    public void addProduct(Product product){
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
    public List<Product> getProductList(){
        return (List<Product>) productRepository.findAll();
    }
    public List<Product> getFilteredProductList(String title, Double minPrice, Double maxPrice){
        List<Product> allProducts = (List<Product>) productRepository.findAll();
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> {
                    String prodTitleLow = product.getTitle().toLowerCase();
                    String filtTitleLow = title != null ? title.toLowerCase() : "ladno";
                    return (title == null || prodTitleLow.contains(filtTitleLow));
                })
                .filter(product -> (minPrice == null || product.getPrice() >= minPrice))
                .filter(product -> (maxPrice == null || product.getPrice() <= maxPrice))
                .collect(Collectors.toList());
        return filteredProducts;
    }
    public List<Product> getProductListWithFilters(String substr, Double minPrice, Double maxPrice, Pageable pageable){
        Specification<Product> filters = Specification.where(null);
        if (substr != null) {
            filters = filters.and(ProductSpecs.containsWord(substr));
        }

        if (minPrice != null) {
            filters = filters.and(ProductSpecs.priceGreaterThan(minPrice));
        }

        if (maxPrice != null) {
            filters = filters.and(ProductSpecs.priceLessThan(maxPrice));
        }
        return productRepository.findAll(filters, pageable).getContent();
    }
}
