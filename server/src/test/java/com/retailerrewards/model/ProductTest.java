package com.retailerrewards.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductTest {

  private Product buildInvoice() {
    return new Product("Gaming Headset", 89.99);
  }

  @Test
  void buildTestProduct() {
    Product product = buildInvoice();
    assertEquals("Gaming Headset", product.getName());
    assertEquals(89.99, product.getPrice());
  }

  @Test
  void testSetName() {
    Product product = buildInvoice();
    product.setName("RGB Keyboard");
    assertEquals("RGB Keyboard", product.getName());
  }

  @Test
  void testSetPrice() {
    Product product = buildInvoice();
    product.setPrice(119.99);
    assertEquals(119.99, product.getPrice());
  }

  @Test
  void testToStringContainsNameAndPrice() {
    Product product = buildInvoice();
    String toString = product.toString();
    assertTrue(toString.contains("Gaming Headset"));
    assertTrue(toString.contains("89.99"));
  }

  @Test
  void testProductsAreEqual() {
    Product a = buildInvoice();
    Product b = buildInvoice();
    assertEquals(a, b);
  }
}
