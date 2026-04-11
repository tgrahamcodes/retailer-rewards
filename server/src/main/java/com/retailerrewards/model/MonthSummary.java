package com.retailerrewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MonthSummary {
    private String month;
    private List<Invoice> invoices;
    private double totalSpent;
    private int pointsEarned;
}