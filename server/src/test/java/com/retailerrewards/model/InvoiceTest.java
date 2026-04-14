package com.retailerrewards.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class InvoiceTest {

  private Invoice buildInvoice() {
    return new Invoice(
        "Jane Doe",
        "01-08-2026",
        List.of(new Product("Wireless Earbuds", 79.99), new Product("Phone Case", 19.99)),
        99.98,
        49);
  }

  @Test
  void testBuildInvoice() {
    Invoice invoice = buildInvoice();
    assertEquals("Jane Doe", invoice.getCustomerName());
    assertEquals("01-08-2026", invoice.getDate());
    assertEquals(99.98, invoice.getTotal());
    assertEquals(49, invoice.getPointsEarned());
    assertEquals(2, invoice.getProducts().size());
  }

  @Test
  void testUpdateNameValue() {
    Invoice invoice = buildInvoice();
    invoice.setCustomerName("John Grant");
    assertEquals("John Grant", invoice.getCustomerName());
  }

  @Test
  void testUpdateTotalValue() {
    Invoice invoice = buildInvoice();
    invoice.setTotal(149.99);
    assertEquals(149.99, invoice.getTotal());
  }

  @Test
  void testUpdatePointsEarnedValue() {
    Invoice invoice = buildInvoice();
    invoice.setPointsEarned(100);
    assertEquals(100, invoice.getPointsEarned());
  }

  @Test
  void testUpdateProductsValue() {
    Invoice invoice = buildInvoice();
    List<Product> testProducts = List.of(new Product("Keyboard", 89.99));
    invoice.setProducts(testProducts);
    assertEquals(1, invoice.getProducts().size());
    assertEquals("Keyboard", invoice.getProducts().get(0).getName());
  }

  @Test
  void testInvoicesAreEqual() {
    Invoice a = buildInvoice();
    Invoice b = buildInvoice();
    assertEquals(a, b);
  }

  @Test
  void testInvoicesAreEqualHashCodes() {
    Invoice a = buildInvoice();
    Invoice b = buildInvoice();
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void testInvoicesAreNotEqual() {
    Invoice a = buildInvoice();
    Invoice b = buildInvoice();
    b.setTotal(200.00);
    assertNotEquals(a, b);
  }

  @Test
  void testContainsCustomerName() {
    Invoice invoice = buildInvoice();
    assertTrue(invoice.toString().contains("Jane Doe"));
  }

  @Test
  void testInvoiceToStringContainsTotal() {
    Invoice invoice = buildInvoice();
    assertTrue(invoice.toString().contains("99.98"));
  }
}
