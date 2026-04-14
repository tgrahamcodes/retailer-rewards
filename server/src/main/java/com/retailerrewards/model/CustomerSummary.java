package com.retailerrewards.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSummary {
  private String customerName;
  private double totalSpent;
  private int totalRewards;
  private List<MonthSummary> months;
}
