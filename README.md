# Avenga Test Project

API test automation project using **RestAssured**, **JUnit 5**, and **Allure** reporting.

## 🧪 Test Report

The latest test execution report is available here:  
👉 [Allure Report](https://andriichorniak.github.io/avenga-test/)

## ⚙️ Project Setup

### ✅ Prerequisites

- Java 21
- Maven
- Allure CLI
- Git

### 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/AndriiChorniak/avenga-test.git
   cd avenga-test

2. Run test:
   ```bash
    mvn clean test allure:report
   ```
The code is designed to make it easy possible to inject the the project as a dependency in other projects (e.x UI test automation framework).
There is CI flow using GitHub Actions that runs tests on every push to the `master` branch. 
The results are published to the [Allure Report](https://andriichorniak.github.io/avenga-test/).