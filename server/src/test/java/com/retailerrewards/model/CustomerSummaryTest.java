package com.retailerrewards.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CustomerSummaryTest {

  private CustomerSummary buildCustomerSummary() {
    return new CustomerSummary(
        "Jane Doe",
        99.98,
        49,
        List.of(
            new MonthSummary(
                "01",
                List.of(
                    new Invoice(
                        "Jane Doe",
                        "01-08-2026",
                        List.of(new Product("Keyboard", 49.99), new Product("Mouse", 49.99)),
                        99.98,
                        49)),
                99.98,
                49)));
  }

  @Test
  void testBuildCustomerSummary() {
    CustomerSummary summary = buildCustomerSummary();
    assertEquals("Jane Doe", summary.getCustomerName());
    assertEquals(99.98, summary.getTotalSpent());
    assertEquals(49, summary.getTotalRewards());
    assertEquals(1, summary.getMonths().size());
    assertEquals("01", summary.getMonths().get(0).getMonth());
  }

  @Test
  void testUpdateCustomerName() {
    CustomerSummary summary = buildCustomerSummary();
    summary.setCustomerName("John Doe");
    assertEquals("John Doe", summary.getCustomerName());
  }

  @Test
  void testUpdateTotalSpent() {
    CustomerSummary summary = buildCustomerSummary();
    summary.setTotalSpent(199.99);
    assertEquals(199.99, summary.getTotalSpent());
  }

  @Test
  void testUpdateTotalRewards() {
    CustomerSummary summary = buildCustomerSummary();
    summary.setTotalRewards(99);
    assertEquals(99, summary.getTotalRewards());
  }

  @Test
  void testUpdateMonths() {
    CustomerSummary summary = buildCustomerSummary();
    List<MonthSummary> newMonths =
        List.of(
            new MonthSummary(
                "02",
                List.of(
                    new Invoice(
                        "John Doe",
                        "02-08-2026",
                        List.of(new Product("Laptop", 999.99)),
                        999.99,
                        499)),
                999.99,
                499));
    summary.setMonths(newMonths);
    assertEquals(1, summary.getMonths().size());
    assertEquals("02", summary.getMonths().get(0).getMonth());
  }
}
