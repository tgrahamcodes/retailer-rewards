package com.retailerrewards.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthSummary {
  private String month;
  private List<Invoice> invoices;
  private double totalSpent;
  private int pointsEarned;
}
