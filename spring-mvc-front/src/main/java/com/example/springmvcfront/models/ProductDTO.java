package com.example.springmvcfront.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private int views;
    //{"id":1,"title":"Milk","description":"Calcium+","price":650.0,"views":16}
}
