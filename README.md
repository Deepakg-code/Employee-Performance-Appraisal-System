# Employee Performance Appraisal System

## Overview
The **Employee Performance Appraisal System** is a backend REST API designed to evaluate and manage employee performance efficiently. It enables HR departments to track employee performance, apply a bell curve distribution for fair appraisals, and generate reports based on predefined metrics.

## Table of Contents
- [Technologies](#technologies)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [License](#license)

## Technologies
- Java 21
- Spring Boot 3.3.x
- Hibernate & JPA
- MySQL
- Maven 3.8.x
- JUnit & Mockito (for unit testing)

## Features
- **Employee Performance Tracking**: Maintain records of employee performance over time.
- **Bell Curve Calculation**: Categorize employees based on statistical performance distribution.
- **Automated Report Generation**: Generate detailed performance analysis reports.
- **Data Persistence**: Store and retrieve employee records using MySQL.
- **Comprehensive Testing**: Includes unit and integration tests to ensure reliability.

## Requirements
To run this project, ensure you have:
- Java 21 or later installed
- Maven 3.8.x or above
- MySQL installed and configured
- Any preferred IDE (IntelliJ IDEA, Eclipse, Spring Tool Suite)

## Installation
### 1. Clone the Repository:
```bash
git clone https://github.com/Deepakg-code/Employee-Performance-Appraisal-System.git
cd Employee-Performance-Appraisal-System
```

### 2. Configure the Database:
Modify the `application.yml` file with your MySQL database credentials:
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

### 3. Install Dependencies:
```bash
mvn clean install
```

### 4. Run the Application:
```bash
mvn spring-boot:run
```
The application will start at `http://localhost:8081` by default.

## API Endpoints
### Base URL: `/api/v1`
| Method | Endpoint | Description |
|--------|-------------------------------|------------------------------------|
| GET    | `/performance/bell-curve`      | Get bell curve performance data  |
| POST   | `/employee`                    | Add a new employee               |
| GET    | `/employee/{employeeId}`       | Get employee details by ID       |
| GET    | `/employees`                   | Get all employees                |
| PUT    | `/employee/{employeeId}`       | Update employee details          |
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

## Standard Percentage

The system uses the following standard percentage distribution, which can be updated in certain situations:

| Category ID | Rating | Standard Percentage |
|------------|--------|---------------------|
| 1          | A      | 10%                 |
| 2          | B      | 20%                 |
| 3          | C      | 40%                 |
| 4          | D      | 20%                 |
| 5          | E      | 10%                 |

## Testing

You can test the API using Postman or any API testing tool by sending requests to the provided endpoints.

### Unit Tests Is Included Only For PerformanceServiceImplTest.java
To run unit tests, execute
EmployeePerformanceAppraisalApiApplicationTests.java in IntelliJ IDEA.

## License
This project is open-source and available for learning and development purposes.

