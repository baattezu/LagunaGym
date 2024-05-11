package com.example.springmvcfront.controllers;



import com.example.springmvcfront.models.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private RestTemplate restTemplate; // If using RestTemplate

    private final String PRODUCTS_REST_API_URL = "http://localhost:8765/product-service/api/products";
    @GetMapping("/{id}")
    public String singleProductPage(Model model, @PathVariable(name = "id") String id){
        ProductDTO productDTO = restTemplate.getForEntity(PRODUCTS_REST_API_URL + "/ "+id, ProductDTO.class).getBody();
        model.addAttribute("product", productDTO);
        return "product";
    }


    @GetMapping
    public String productsPage(Model model){
        ProductDTO[] products = restTemplate.getForEntity(PRODUCTS_REST_API_URL, ProductDTO[].class).getBody();
        model.addAttribute("products",
                Arrays.asList(products));
        model.addAttribute("produc55t",
                new ProductDTO());
        return "products";
    }

}
