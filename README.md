# Library Management System

A backend project for library management using Spring Boot with JWT authentication.

## Overview

This Spring Boot application provides a comprehensive library management system through RESTful APIs. It features book management, borrowing functionality, and user authentication with role-based access control. Built with MySQL for data persistence and JWT for secure authentication.

## Features

### Authentication & Authorization
- User registration and login with JWT authentication
- Role-based access control:
  - **LIBRARIAN**: Full book management capabilities
  - **USER**: Book viewing and borrowing privileges

### Book Management
- Complete CRUD operations for books
- Advanced search functionality (title/author)
- Customizable sorting (title/publication date)

### Borrowing System
- Book borrowing and return functionality
- Quantity tracking and management

### Data Validation
- Input validation for book operations
- Comprehensive error handling with appropriate HTTP status codes

## Tech Stack

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security with JWT
- MySQL
- Lombok
- Maven

## Getting Started

### Prerequisites
- Java 21
- Maven
- MySQL 8.0+
- Postman (for API testing)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/collinswamucii/library-management
   cd library-management
   ```

2. **Set up MySQL**
   ```sql
   CREATE DATABASE library;
   ```

3. **Configure application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   spring.jpa.hibernate.ddl-auto=update

   app.jwtSecret=yourSecretKey
   app.jwtExpirationMs=86400000
   ```

4. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Documentation

### Authentication Endpoints

#### Register User
- **POST** `/auth/register`
- **Body:**
  ```json
  {
      "username": "librarian",
      "password": "password123",
      "roles": ["LIBRARIAN"]
  }
  ```

#### Login
- **POST** `/auth/login`
- **Body:**
  ```json
  {
      "username": "librarian",
      "password": "password123"
  }
  ```

### Book Endpoints

#### Get Books
- **GET** `/books`
- **Query Parameters:**
  - `search`: Filter by title/author
  - `sortBy`: Sort field (title/publicationDate)
  - `order`: Sort direction (asc/desc)
- **Headers:** `Authorization: Bearer <jwt_token>`

#### Get Book by ID
- **GET** `/books/{id}`
- **Headers:** `Authorization: Bearer <jwt_token>`

#### Create Book (LIBRARIAN)
- **POST** `/books`
- **Headers:** `Authorization: Bearer <jwt_token>`
- **Body:**
  ```json
  {
      "title": "Spring in Action",
      "author": "Craig Walls",
      "isbn": "978-1617294945",
      "quantity": 5,
      "publicationDate": "2023-01-01"
  }
  ```

### Borrowing Endpoints

#### Borrow Book
- **POST** `/borrow`
- **Headers:** `Authorization: Bearer <jwt_token>`
- **Body:**
  ```json
  {
      "bookId": 1,
      "borrowerName": "John Smith",
      "borrowDate": "2025-04-28"
  }
  ```

#### Return Book
- **POST** `/return`
- **Headers:** `Authorization: Bearer <jwt_token>`
- **Body:**
  ```json
  {
      "bookId": 1,
      "borrowerName": "John Smith",
      "borrowDate": "2025-04-28",
      "returnDate": "2025-04-29"
  }
  ```

## Testing

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/16208567-8a90e276-cbfc-4e6e-b83b-7959b37655ba?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D16208567-8a90e276-cbfc-4e6e-b83b-7959b37655ba%26entityType%3Dcollection%26workspaceId%3Df475ef25-1f30-4c4c-bec9-98acf751de46)

## Troubleshooting

### Common Issues
1. **MySQL Connection Problems**
   - Verify MySQL service is running
   - Check database existence
   - Validate credentials in application.properties

2. **JWT Issues**
   - Ensure valid JWT secret key configuration
   - Check token expiration settings

3. **Access Denied (403)**
   - Verify user role permissions
   - Validate JWT token

4. **Resource Not Found (404)**
   - Confirm resource IDs exist
   - Check API endpoint URLs
