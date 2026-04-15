# retailer-rewards

A retailer rewards program that awards points based on customer purchases. Customers earn 2 points for every dollar spent over $100 per transaction, plus 1 point for every dollar spent between $50 and $100 per transaction.

Example: A $120 purchase earns 2×$20 + 1×$50 = 90 points.

## Overview

Calculate reward points earned for each customer per month and total across a three-month period. This is a React SPA that simulates asynchronous API calls to fetch transaction data.

## Tech Stack

Built with React, JavaScript, Vite, Spring Boot, and H2 in-memory database. Designed with Adobe XD.

## How to get things up and running

- Install node (Windows: `winget install node`; Mac `brew install node`; Linux `apt install node`)
- `cd client/`
- `npm i`
- `npm run dev`

To run the backend:
  - `cd server`
  - `./mvnw spring-boot:run`
    

Naigate to localhost:5147
