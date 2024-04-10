package com.lagunagym.LagunaGym.controllers;

import com.lagunagym.LagunaGym.models.Product;
import com.lagunagym.LagunaGym.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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


    @GetMapping("/{id}")
    public String singleProductPage(Model model, @PathVariable(name = "id") String id){
        Product product = productService.getViewOfProduct(id);
        model.addAttribute("product", product);
        return "product";
    }


    @GetMapping
    public String productsPage(Model model,
                              @RequestParam(value = "page",required = false) String page,
                              @RequestParam(value = "filterByTitle",required = false) String title,
                              @RequestParam(value = "minPrice",required = false) Double minPrice,
                              @RequestParam(value = "maxPrice",required = false) Double maxPrice){
        Integer currentPage = 0;
        if (page != null){
             currentPage = Integer.parseInt(page);
        }
        model.addAttribute("products",
                productService.getProductListWithFilters(title, minPrice, maxPrice, PageRequest.of(currentPage,4)));
        model.addAttribute("product",
                new Product());
        model.addAttribute("top3", productService.getTop3());
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product") Product product){
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
