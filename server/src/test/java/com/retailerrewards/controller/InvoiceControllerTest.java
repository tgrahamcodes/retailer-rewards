package com.retailerrewards.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailerrewards.model.CustomerSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  private CustomerSummary getSummary(String id) throws Exception {
    MvcResult result =
        mockMvc
            .perform(get("/api/customer/{id}/invoices", id).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    return objectMapper.readValue(result.getResponse().getContentAsString(), CustomerSummary.class);
  }

  @Test
  void testGetInvoiceReturns200() throws Exception {
    mockMvc
        .perform(get("/api/customer/123/invoices"))
        .andExpect(status().isOk()) // 200 OK
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    mockMvc
        .perform(get("/api/customer/456/invoices"))
        .andExpect(status().isOk()) // 200 OK
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetInvoicesForUnknownID() throws Exception {
    MvcResult result =
        mockMvc.perform(get("/api/customer/999/invoices")).andExpect(status().isOk()).andReturn();
    assertTrue(
        result.getResponse().getContentAsString().isEmpty()
            || result.getResponse().getContentAsString().equals("null"));
  }

  @Test
  void testVerifyCustomer123Name() throws Exception {
    CustomerSummary summary = getSummary("123");
    assertEquals("Jane Smith", summary.getCustomerName());
  }

  @Test
  void testVerifyCustomer456Name() throws Exception {
    CustomerSummary summary = getSummary("456");
    assertEquals("John Doe", summary.getCustomerName());
  }

  @Test
  void testVerifyInvoiceCountCustomer123() throws Exception {
    CustomerSummary summary = getSummary("123");
    int invoiceCount = summary.getMonths().stream().mapToInt(m -> m.getInvoices().size()).sum();
    assertEquals(7, invoiceCount);
  }

  @Test
  void testVerifyInvoiceCountCustomer456() throws Exception {
    CustomerSummary summary = getSummary("456");
    int invoiceCount = summary.getMonths().stream().mapToInt(m -> m.getInvoices().size()).sum();
    assertEquals(6, invoiceCount);
  }

  @Test
  void testVerifyCustomerTotalRewards123() throws Exception {
    CustomerSummary summary = getSummary("123");
    assertEquals(1269, summary.getTotalRewards());
  }

  @Test
  void testVerifyCustomerTotalRewards456() throws Exception {
    CustomerSummary summary = getSummary("456");
    assertEquals(1394, summary.getTotalRewards());
  }

  @Test
  void testVerifyCustomerTotalSpent123() throws Exception {
    CustomerSummary summary = getSummary("123");
    assertEquals(1162.76, summary.getTotalSpent(), 0.01);
  }

  @Test
  void testVerifyCustomerTotalSpent456() throws Exception {
    CustomerSummary summary = getSummary("456");
    assertEquals(1149.79, summary.getTotalSpent(), 0.01);
  }

  @Test
  void testVerifyAllInvoicesHaveNonZeroPoints() throws Exception {
    CustomerSummary summary = getSummary("123");
    boolean allNonZero =
        summary.getMonths().stream()
            .flatMap(m -> m.getInvoices().stream())
            .allMatch(inv -> inv.getPointsEarned() > 0);
    assertTrue(allNonZero, "Every invoice total is above $50, so no invoice should have 0 points");
  }
}
