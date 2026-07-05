# 🚀 AutomationExercise Selenium Test Automation Framework

<p align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-DD0031?style=for-the-badge)
![POM](https://img.shields.io/badge/Design-Page%20Object%20Model-blue?style=for-the-badge)
![Allure](https://img.shields.io/badge/Reporting-Allure%20Report-purple?style=for-the-badge)
![Status](https://img.shields.io/badge/Project-Completed-brightgreen?style=for-the-badge)

</p>

---

# 📖 About the Framework

This project is a **scalable Selenium Automation Framework** built for testing the **Automation Exercise** web application.

It is designed using industry best practices including:
- Page Object Model (POM)
- Data-Driven Testing using JSON
- TestNG for test execution management
- WebDriverManager for browser handling
- Allure Reports for test reporting

The framework covers **26 functional test cases + 4 additional negative test cases** to ensure both functional and edge-case validation.

---

# 🏗 Framework Architecture

```text
Test Classes
   ↓
Page Object Model (POM)
   ↓
Utility Layer
(ConfigReader, JSONReader, DriverFactory)
   ↓
WebDriverManager
   ↓
Browser (Chrome / Edge / Firefox)
```

---

# 🛠 Tech Stack

- Java
- Selenium WebDriver
- TestNG
- WebDriverManager
- JSON (Test Data)
- Maven
- Allure Reports

---

# ✨ Framework Features

✔ Page Object Model (POM)  
✔ Data-Driven Testing using JSON  
✔ Cross-browser Testing (Chrome, Edge, Firefox)  
✔ Test Execution via TestNG XML  
✔ Configurable environment using properties file  
✔ Reusable utility classes  
✔ WebDriverManager integration  
✔ Allure Reporting Integration  
✔ Modular and scalable framework design  

---

# 📂 Project Structure

```text
src
│
├── test/java
│   ├── tests
│   ├── pages
│   ├── utils
│   └── base
│
├── test/resources
│   ├── testdata (JSON files)
│   ├── config.properties
│   └── testng.xml
```

---

# 📊 Test Coverage

| Area | Covered |
|------|--------|
| User Registration | ✅ |
| Login | ✅ |
| Product Search | ✅ |
| Product Details | ✅ |
| Cart Management | ✅ |
| Checkout Flow | ✅ |
| Contact Forms | ✅ |
| Newsletter Subscription | ✅ |
| Negative Scenarios | ✅ |

- Total Test Cases: **26**
- Bonus Negative Test Cases: **4**

---

# ▶️ How to Run the Framework

### 1. Clone the repository
```bash
git clone https://github.com/your-username/AutomationExercise-Selenium-Framework
```

### 2. Install dependencies
```bash
mvn clean install
```

### 3. Run TestNG Suite
```bash
testng.xml
```

---

# 📸 Framework Evidence

## 📌 Project Structure (IntelliJ)
<img src="assets/project-structure.png" width="900"/>

## 📌 Test Execution
<img src="assets/test-execution.png" width="900"/>

## 📌 Cross Browser Execution
<img src="assets/cross-browser.png" width="900"/>

## 📌 JSON Test Data
<img src="assets/json-data.png" width="900"/>

## 📌 Allure Report Dashboard
<img src="assets/allure-report.png" width="900"/>

## 📌 Failed Test (Negative Scenario)
<img src="assets/failed-test.png" width="900"/>

---

# 📈 Allure Reporting

This framework integrates **Allure Reports** to generate detailed HTML reports including:

- Test execution status
- Step-by-step breakdown
- Failed test analysis
- Execution timeline
- Suite-wise results

To generate report:

```bash
allure serve allure-results
```

---

# 🧪 Additional Negative Test Scenarios

In addition to the 26 functional test cases, **4 additional negative test cases** were designed to validate edge cases and system robustness.

These tests intentionally trigger failure scenarios to ensure proper validation and error handling.

---

# 🐞 Bug Reporting

Any unexpected behavior observed during execution is documented using structured bug reports including:

- Test conditions
- Steps to reproduce
- Expected vs actual results
- Screenshots
- Environment details

---

# 💡 Key Learning Outcomes

- Building a real-world automation framework from scratch
- Implementing Page Object Model effectively
- Handling test data dynamically using JSON
- Managing cross-browser testing
- Generating detailed test reports using Allure
- Structuring scalable test automation frameworks

---

# 👨‍💻 Author

**Your Name**

Software Test Engineer

🏅 ISTQB® Certified Tester Foundation Level (CTFL v4.0)

📧 Email: your-email

🔗 LinkedIn: your-linkedin

---

# ⭐ If you like this project

Feel free to ⭐ the repository and connect with me on LinkedIn for QA and automation discussions.
