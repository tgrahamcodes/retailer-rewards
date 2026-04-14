package com.retailerrewards.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {
  private String customerName;
  private String date;
  private List<Product> products;
  private double total;
  private int pointsEarned;
}
