package com.retailerrewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RewardsApplication {

  /**
   * Main method that starts the backend of the UI.
   *
   * @param args command-line arguments, if used
   */
  public static void main(String[] args) {
    SpringApplication.run(RewardsApplication.class, args);
  }
}
