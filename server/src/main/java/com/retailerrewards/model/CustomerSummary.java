package com.retailerrewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerSummary {
    private String customerName;
    private double totalSpent;
    private int totalRewards;
    private List<MonthSummary> months;
}