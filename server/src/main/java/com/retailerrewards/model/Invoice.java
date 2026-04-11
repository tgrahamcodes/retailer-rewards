package com.retailerrewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Invoice {
    private String customerName;
    private String date;
    private List<Product> products;
    private double total;
    private int pointsEarned;
}