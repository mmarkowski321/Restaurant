# 🍽 Restaurant Table Management API

This project is a **REST API** for managing restaurant table lifecycle, built using **Spring Boot**. It allows handling reservations, assigning products to tables, and monitoring their statuses.

🚀 **This was my first Spring Boot project**, through which I learned fundamental concepts such as:
- **application.yml** configuration for connecting to **PostgreSQL**
- The **Service Component** architecture in Spring Boot
- Using **JPA Repository** for database operations
- Writing **unit and integration tests** with **JUnit & Mockito**
- Using **H2 as an in-memory database** for testing  

---

## 📌 Features

✅ **Manage tables and products** – stored in **PostgreSQL**  
✅ **API accessible via POSTMAN/Curl**  
✅ **Follows a 3-layer architecture** (Controller, Service, Repository)  
✅ **State validation for tables** – e.g., a non-paid table cannot be closed  
✅ **Assigning products to tables** 
✅ **Unit & integration testing** – using **H2 database**  

---

### 1️⃣ **Clone the repository**
git clone https://github.com/mmarkowski321/Restaurant
cd restaurant-table-management
