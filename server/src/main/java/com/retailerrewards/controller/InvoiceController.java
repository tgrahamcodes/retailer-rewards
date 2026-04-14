package com.retailerrewards.controller;

import com.retailerrewards.model.CustomerSummary;
import com.retailerrewards.model.Invoice;
import com.retailerrewards.model.MonthSummary;
import com.retailerrewards.model.Product;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class InvoiceController {

  /**
   * Method to calculate reward points based on total amount. 2pts per dollar over 100 + 50 points
   * for the first 50-100.
   *
   * @param total The total on the invoice.
   * @return int The reward points calculated from the total.
   */
  private int calculatePoints(double total) {
    int rewardPoints = 0;
    if (total > 100) {
      rewardPoints += (int) ((total - 100) * 2);
      rewardPoints += 50;
    } else if (total > 50) {
      rewardPoints += (int) (total - 50);
    }
    return rewardPoints;
  }

  /**
   * Method to build the customer summary from raw invoices
   *
   * @param name The customer's name.
   * @param invoices The list of invoices for the the given customer.
   * @return CustomerSummary
   */
  private CustomerSummary buildSummary(String name, List<Invoice> invoices) {
    List<Invoice> withPoints =
        invoices.stream()
            .map(
                inv ->
                    new Invoice(
                        inv.getCustomerName(),
                        inv.getDate(),
                        inv.getProducts(),
                        inv.getTotal(),
                        calculatePoints(inv.getTotal())))
            .toList();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    Map<String, List<Invoice>> byMonth =
        withPoints.stream()
            .collect(
                Collectors.groupingBy(
                    inv -> {
                      LocalDate date = LocalDate.parse(inv.getDate(), formatter);
                      return date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                          + " "
                          + date.getYear();
                    }));

    List<MonthSummary> months =
        byMonth.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(
                entry -> {
                  List<Invoice> monthInvoices = entry.getValue();
                  double monthSpent = monthInvoices.stream().mapToDouble(Invoice::getTotal).sum();
                  int monthPoints = monthInvoices.stream().mapToInt(Invoice::getPointsEarned).sum();
                  return new MonthSummary(
                      entry.getKey(),
                      monthInvoices,
                      Math.round(monthSpent * 100.0) / 100.0,
                      monthPoints);
                })
            .toList();

    double totalSpent = withPoints.stream().mapToDouble(Invoice::getTotal).sum();
    int totalRewards = withPoints.stream().mapToInt(Invoice::getPointsEarned).sum();

    return new CustomerSummary(name, Math.round(totalSpent * 100.0) / 100.0, totalRewards, months);
  }

  // Simulated raw invoice data for two customers (IDs "123" and "456", assumed no
  // need for db access in this example)
  private final Map<String, List<Invoice>> rawInvoices =
      Map.of(
          "123",
              List.of(
                  new Invoice(
                      "Jane Smith",
                      "01-08-2026",
                      List.of(
                          new Product("Wireless Earbuds", 79.99),
                          new Product("Phone Case", 19.99),
                          new Product("Screen Protector 2pk", 14.99)),
                      114.97,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "01-22-2026",
                      List.of(
                          new Product("Smart Watch Band", 29.99),
                          new Product("USB-C Cable 6ft", 12.99),
                          new Product("Wall Charger 45W", 34.99),
                          new Product("Portable Battery 20000mAh", 49.99)),
                      127.96,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "02-03-2026",
                      List.of(
                          new Product("Mechanical Keyboard", 89.99),
                          new Product("Monitor Stand", 34.99),
                          new Product("Webcam 1080p", 79.99)),
                      204.97,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "02-14-2026",
                      List.of(
                          new Product("Noise Cancelling Headphones", 149.99),
                          new Product("Headphone Stand", 24.99),
                          new Product("Audio Splitter", 9.99)),
                      184.97,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "02-27-2026",
                      List.of(
                          new Product("4K Webcam", 119.99),
                          new Product("Ring Light 10\"", 39.99),
                          new Product("Mic Arm Stand", 29.99),
                          new Product("USB Condenser Mic", 59.99)),
                      249.96,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "03-02-2026",
                      List.of(
                          new Product("Portable SSD 1TB", 109.99),
                          new Product("Card Reader USB-C", 19.99),
                          new Product("Cable Organizer Kit", 14.99)),
                      144.97,
                      0),
                  new Invoice(
                      "Jane Smith",
                      "03-09-2026",
                      List.of(
                          new Product("Wireless Charging Pad", 39.99),
                          new Product("Smart Plug 4pk", 29.99),
                          new Product("LED Desk Lamp", 44.99),
                          new Product("Extension Cord 6ft", 19.99)),
                      134.96,
                      0)),
          "456",
              List.of(
                  new Invoice(
                      "John Doe",
                      "01-06-2026",
                      List.of(
                          new Product("Gaming Headset", 89.99),
                          new Product("Controller Charging Dock", 34.99),
                          new Product("HDMI 2.1 Cable", 19.99)),
                      144.97,
                      0),
                  new Invoice(
                      "John Doe",
                      "01-20-2026",
                      List.of(
                          new Product("RGB Keyboard", 119.99),
                          new Product("Gaming Mouse", 59.99),
                          new Product("Mouse Pad XL", 29.99),
                          new Product("Wrist Rest", 19.99)),
                      229.96,
                      0),
                  new Invoice(
                      "John Doe",
                      "02-08-2026",
                      List.of(
                          new Product("Smart LED Strip Lights", 34.99),
                          new Product("Bluetooth Speaker", 79.99),
                          new Product("Phone Stand", 19.99),
                          new Product("Cable Organizer", 14.99)),
                      149.96,
                      0),
                  new Invoice(
                      "John Doe",
                      "02-22-2026",
                      List.of(
                          new Product("Streaming Mic", 99.99),
                          new Product("Pop Filter", 14.99),
                          new Product("Boom Arm", 34.99)),
                      149.97,
                      0),
                  new Invoice(
                      "John Doe",
                      "03-05-2026",
                      List.of(
                          new Product("Action Camera", 199.99),
                          new Product("Camera Mount", 24.99),
                          new Product("32GB SD Card 2pk", 29.99)),
                      254.97,
                      0),
                  new Invoice(
                      "John Doe",
                      "03-19-2026",
                      List.of(
                          new Product("Graphic Tablet", 149.99),
                          new Product("Stylus Pen 2pk", 19.99),
                          new Product("Drawing Glove", 9.99),
                          new Product("Screen Calibrator", 39.99)),
                      219.96,
                      0)));

  /**
   * @param id
   * @return CustomerSummary
   */
  // API endpoint to retrieve customer summary by ID
  @GetMapping("/customer/{id}/invoices")
  public CustomerSummary getInvoices(@PathVariable String id) {
    List<Invoice> invoices = rawInvoices.get(id);
    if (invoices == null) return null;
    return buildSummary(invoices.get(0).getCustomerName(), invoices);
  }
}
