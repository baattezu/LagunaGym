package com.lagunagym.LagunaGym.controllers;

import com.lagunagym.LagunaGym.models.Person;
import com.lagunagym.LagunaGym.models.Product;
import com.lagunagym.LagunaGym.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productPage(Model model,
                              @RequestParam(value = "filterByTitle",required = false) String title,
                              @RequestParam(value = "minPrice",required = false) Double minPrice,
                              @RequestParam(value = "maxPrice",required = false) Double maxPrice){
        model.addAttribute("products", productService.getFilteredProductList(title,minPrice,maxPrice));
        model.addAttribute("product", productService.createProductForForm());
        return "products";
    }
    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product") Product product){
        System.out.println(product.getId());
        productService.addProduct(product);
        return "redirect:/products";
    }
    @PostMapping("/edit")
    public String editProduct(@RequestParam("ed_id") Long id, @RequestParam("ed_title") String title
            , @RequestParam("ed_desc") String desc, @RequestParam("ed_price") Double price){
        productService.updateProduct(id,title,desc,price);
        return "redirect:/products";
    }
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam(value="del_id") Long id){
        productService.removeProduct(id);
        return "redirect:/products";
    }
}
