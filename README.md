# 🍽️ Restaurant Management System

A **Spring Boot + Thymeleaf** web application that simulates a simple restaurant management system.  
It allows administrators to manage menu items, inventory, orders, and customer reviews in one dashboard.  

---

## 🚀 Features

✅ **Authentication & Roles**
- Two roles: `General Manager (GM)` and `Assistant General Manager (AGM)`  
- Secure login/logout with Spring Security  
- Predefined demo accounts included  

✅ **Menu Management**
- Add, edit, delete, and list menu items  
- Categories: appetizers, main courses, desserts, and drinks  

✅ **Inventory Management**
- Track inventory quantity, units, and prices  
- Prevent over-ordering and view all stock items  

✅ **Order Management**
- Create and update purchase orders for inventory items  
- View and edit order status (Pending, Completed, Cancelled)  

✅ **Reviews Section**
- Displays customer feedback and ratings  

---

## 🧩 Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot 3, Spring MVC, Spring Security |
| Frontend | Thymeleaf, HTML5, CSS3 |
| Database | H2 (in-memory) |
| Build Tool | Maven |
| Language | Java 17+ |

---

## 🧠 Project Structure

src/
├── main/
│ ├── java/com/example/project/
│ │ ├── controller/ # Web controllers
│ │ ├── entity/ # JPA entities
│ │ ├── repository/ # Spring Data repositories
│ │ └── security/ # Security config and services
│ └── resources/
│ ├── static/css/ # Styles
│ ├── templates/ # Thymeleaf views
│ ├── schema.sql # Database schema
│ ├── data.sql # Sample data
│ └── application.properties
└── test/ # Unit tests

---

## 🧑‍💻 How to Run Locally

### 1️⃣ Clone the Repository

git clone https://github.com/your-username/restaurant-management.git
cd restaurant-management


2️⃣ Run with Maven

Make sure you have Java 17+ and Maven installed.

Alternatively, build the JAR:

mvn clean package
java -jar target/restaurant-management-0.0.1-SNAPSHOT.jar

http://localhost:8080


🔐 Demo Credentials
Role	Email	Password
General Manager	general_manager@restaurant.ca
	12345
Assistant GM	assistant_manager@restaurant.ca
	12345

You can log in with either role and explore the dashboard.

🧰 H2 Console (Optional)

You can view the in-memory database from:

http://localhost:8080/h2-console


Use these settings:

JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password

📦 Default Data

The app loads sample data automatically:

20+ Menu Items

50+ Inventory Items

Multiple Orders

Several Customer Reviews

Predefined Users and Roles

📸 Pages Overview
Page	URL	Description
Login	/login	Secure login for GM/AGM
Dashboard	/	Quick overview
Menu	/menu	Manage menu items
Inventory	/inventory	Manage stock
Orders	/order	Manage supply orders
Reviews	/reviews	View customer feedback
🧹 Resetting the App

Since it uses an in-memory H2 database, all data resets when the app restarts.
You can edit data.sql to preload your own demo content.

📄 License

This project is provided for educational and portfolio purposes.
You’re free to reuse or modify it as long as attribution remains.
