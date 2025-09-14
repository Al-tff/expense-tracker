# Expense Tracker REST API  

A **Spring Boot REST API** project for managing personal expenses.  
This backend application demonstrates how to build and structure APIs using **Java, Maven, Hibernate/JPA, and MySQL**, with clean and maintainable code practices.  

---

## Features  
- ➕ Add, update, and delete expenses  
- 📂 Categorize expenses (Food, Travel, Bills, etc.)  
- 📊 View all expenses or filter by category/date  
- 🔍 Retrieve a single expense by ID  
- 🗄️ Database persistence with MySQL & JPA/Hibernate  
- 🌱 REST API design following best practices  

---

## 🛠️ Tech Stack  
- **Java 17+**  
- **Spring Boot** (REST, Data JPA)  
- **Hibernate/JPA**  
- **MySQL** (or H2 for in-memory testing)  
- **Maven** (build tool)  

---

## 📂 Project Structure  
expense-tracker/
├── src/main/java/com/projects/expense_tracker/
│ ├── controller/ # REST Controllers
│ ├── model/ # Entities
│ ├── repository/ # JPA Repositories
│ ├── service/ # Business Logic
│ └── ExpenseTrackerApplication.java
├── src/main/resources/
│ ├── application.properties (ignored in Git)
├── pom.xml

---

## ⚙️ Getting Started  

### 1. Clone the repository  
```bash
git clone https://github.com/<your-username>/expense-tracker.git
cd expense-tracker


## 2. Configure the database

Update application.properties (or copy from application-example.properties):

spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

