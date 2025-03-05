# **Employee Performance Appraisal System**

## **Overview**
The **Employee Performance Appraisal System** is a **full-stack web application** that allows HR departments to efficiently track, evaluate, and manage employee performance. It features **a Spring Boot backend** and **a React-based frontend**, implementing a **bell curve appraisal system** to fairly distribute employee performance ratings.

---

## **📌 Table of Contents**
- [Technologies](#technologies)
- [Features](#features)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [License](#license)

---

## **🛠️ Technologies**
### **Backend (Spring Boot)**
- Java 21
- Spring Boot 3.3.x
- Hibernate & JPA
- MySQL
- Maven 3.8.x
- JUnit & Mockito (for unit testing)

### **Frontend (React)**
- React 18+
- Tailwind CSS
- Axios
- React Router
- Recharts (for graph visualization)

---

## **🚀 Features**
- **Employee Management** → Add, update, and delete employees.
- **Performance Tracking** → Evaluate employee ratings over time.
- **Bell Curve Calculation** → Categorize employees based on performance.
- **Dynamic UI** → Interactive dashboard for performance analytics.
- **Data Persistence** → MySQL database integration for employee records.
- **Comprehensive Testing** → Unit and integration tests ensure system reliability.

---

## **🔧 Requirements**
To run this project, ensure you have:
- Java 21 or later installed
- Maven 3.8.x or above
- MySQL installed and configured
- Node.js 18+ (for frontend)
- IntelliJ IDEA & VS Code (Recommended IDEs)

---

## **📂 Project Structure**
```
Employee-Performance-Appraisal-System/
│── springbootbackend/        # Spring Boot backend
│── employee-appraisal-ui/       # React frontend
│── README.md       # Documentation
```

---

## **💻 Backend Setup (Spring Boot)**
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/Deepakg-code/Employee-Performance-Appraisal-System.git
cd Employee-Performance-Appraisal-System/backend
```

### **2️⃣ Configure the Database**
Modify `application.yml` inside the **backend** directory:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/epas_db?createDatabaseIfNotExist=true
    username: your-username
    password: your-password
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true

server:
  port: 8081
```

### **3️⃣ Install Dependencies**
```sh
mvn clean install
```

### **4️⃣ Run the Backend**
```sh
mvn spring-boot:run
```
Backend API will be available at:  
📌 **`http://localhost:8081`**

---

## **🌐 Frontend Setup (React)**
### **1️⃣ Navigate to the Frontend Directory**
```sh
cd ../employee-appraisal-ui
```

### **2️⃣ Install Dependencies**
```sh
npm install
```

### **3️⃣ Start the React App**
```sh
npm start
```
Frontend will be available at:  
📌 **`http://localhost:3000`**

---

## **📡 API Endpoints**
### **Base URL:** `http://localhost:8081`
| Method | Endpoint | Description |
|--------|-------------------------------|------------------------------------|
| GET    | `/performance/bell-curve`      | Get bell curve performance data  |
| POST   | `/employee`                    | Add a new employee               |
| GET    | `/employee/{employeeId}`       | Get employee details by ID       |
| GET    | `/employees`                   | Get all employees                |
| PUT    | `/employee/{employeeId}`       | Update employee details          |
| PUT    | `/employee/{id}/rating`        | Update employee rating           |
| DELETE | `/employee/{employeeId}`       | Delete an employee               |
| POST   | `/category`                    | Add a new category               |
| GET    | `/category/{id}`               | Get category by ID               |
| GET    | `/category`                    | Get all categories               |
| PUT    | `/category/{id}`               | Update category details          |
| DELETE | `/category/{id}`               | Delete a category                |
| GET    | `/appraisal`                   | Get all appraisals               |
| GET    | `/appraisal/employee/{employeeId}` | Get appraisals for an employee |
| POST   | `/appraisal/{employeeId}`      | Create a new appraisal           |
| DELETE | `/appraisal/{appraisalId}`     | Delete an appraisal              |

---

## **📊 Standard Performance Distribution**
The system applies the following standard **bell curve distribution**:

| Category ID | Rating | Standard Percentage |
|------------|--------|---------------------|
| 1          | A      | 10%                 |
| 2          | B      | 20%                 |
| 3          | C      | 40%                 |
| 4          | D      | 20%                 |
| 5          | E      | 10%                 |

---

## **📸 UI Features**
### **Dashboard**
- 🏠 **Welcome Dashboard**
- ➕ **Add Employee**
- ➕ **Add Category**
- 📋 **View Available Categories**

### **Employees**
- 👥 **View all employees**
- ✏️ **Edit ratings**
- ❌ **Delete employees**

### **Bell Curve Performance**
- 📊 **Performance Graph**
- 📌 **Suggested Adjustments for Ratings**

---

## Testing

You can test the API using Postman or any API testing tool by sending requests to the provided endpoints.

### Unit Tests Is Included Only For PerformanceServiceImplTest.java
To run unit tests, execute
EmployeePerformanceAppraisalApiApplicationTests.java in IntelliJ IDEA.

---

## **📜 License**
This project is open-source and available for learning and development purposes.

---
