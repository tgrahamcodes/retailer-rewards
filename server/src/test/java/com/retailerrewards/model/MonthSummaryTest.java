package com.retailerrewards.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class MonthSummaryTest {

  private MonthSummary buildMonthSummary() {
    return new MonthSummary(
        "01",
        List.of(
            new Invoice(
                "Jane Doe",
                "01-08-2026",
                List.of(new Product("Wireless Earbuds", 79.99), new Product("Phone Case", 19.99)),
                99.98,
                49),
            new Invoice(
                "John Smith",
                "01-15-2026",
                List.of(
                    new Product("Bluetooth Speaker", 49.99), new Product("Charging Cable", 9.99)),
                59.98,
                29)),
        159.96,
        78);
  }

  @Test
  void testBuildMonthSummary() {
    MonthSummary summary = buildMonthSummary();
    assertEquals("01", summary.getMonth());
    assertEquals(2, summary.getInvoices().size());
    assertEquals(159.96, summary.getTotalSpent());
    assertEquals(78, summary.getPointsEarned());
  }

  @Test
  void testMonthlySummaryMonth() {
    MonthSummary summary = buildMonthSummary();
    summary.setMonth("02");
    assertEquals("02", summary.getMonth());
  }

  @Test
  void testMonthlySummaryInvoices() {
    MonthSummary summary = buildMonthSummary();
    List<Invoice> newInvoices =
        List.of(
            new Invoice(
                "John Doe", "02-08-2026", List.of(new Product("Laptop", 999.99)), 999.99, 499));
    summary.setInvoices(newInvoices);
    assertEquals(1, summary.getInvoices().size());
    assertEquals("John Doe", summary.getInvoices().get(0).getCustomerName());
  }

  @Test
  void testMonthlySummaryTotalSpent() {
    MonthSummary summary = buildMonthSummary();
    summary.setTotalSpent(149.99);
    assertEquals(149.99, summary.getTotalSpent());
  }

  @Test
  void testMonthlySummaryPointsEarned() {
    MonthSummary summary = buildMonthSummary();
    summary.setPointsEarned(100);
    assertEquals(100, summary.getPointsEarned());
  }

  @Test
  void testMonthlySummaryProducts() {
    MonthSummary summary = buildMonthSummary();
    List<Product> testProducts = List.of(new Product("Keyboard", 89.99));
    summary.setInvoices(List.of(new Invoice("John Doe", "02-08-2026", testProducts, 89.99, 44)));
    assertEquals(1, summary.getInvoices().size());
    assertEquals("John Doe", summary.getInvoices().get(0).getCustomerName());
    assertEquals(1, summary.getInvoices().get(0).getProducts().size());
    assertEquals("Keyboard", summary.getInvoices().get(0).getProducts().get(0).getName());
  }

  @Test
  void testEqualMonthlySummaries() {
    MonthSummary a = buildMonthSummary();
    MonthSummary b = buildMonthSummary();
    assertEquals(a, b);
  }

  @Test
  void testNotEqualMonthlySummaries() {
    MonthSummary a = buildMonthSummary();
    MonthSummary b = buildMonthSummary();
    b.setMonth("02");
    assertNotEquals(a, b);
  }

  @Test
  void testHashCodeEqualsMonthlySummaries() {
    MonthSummary a = buildMonthSummary();
    MonthSummary b = buildMonthSummary();
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void testToStringMonthlySummary() {
    MonthSummary summary = buildMonthSummary();
    assertTrue(summary.toString().contains("01"));
    assertTrue(summary.toString().contains("99.98"));
    assertTrue(summary.toString().contains("49"));
  }
}
