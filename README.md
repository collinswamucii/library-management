## Library Management System

Backend Project for management of a library using Spring Boot with JWT authentication.
Overview

This is a Spring Boot application designed to manage a library system. It provides RESTful APIs to handle book management, borrowing, and user authentication with role-based access control. The application uses MySQL for persistence, JWT for authentication, and includes features like search, sorting, validation, and error handling.

# Features

    User Authentication: Register and login users with JWT-based authentication.
    Role-Based Access Control:
        LIBRARIAN role: Can add, update, and delete books.
        USER role: Can view books and borrow/return books.
    Book Management:
        Add, update, delete, and retrieve books.
        Search books by title or author.
        Sort books by title or publication date.
    Borrowing System:
        Borrow and return books with quantity tracking.
    Validation and Error Handling:
        Input validation for book creation and updates.
        Proper HTTP status codes and error messages (e.g., 404 for missing books, 400 for validation errors).

Technologies Used

    Java 21
    Spring Boot 3.4.5
    Spring Data JPA
    Spring Security with JWT
    MySQL
    Lombok
    Maven

Prerequisites

    Java 21
    Maven
    MySQL (version 8.0 or later)
    Postman (for testing the APIs)

Setup Instructions

    Clone the Repository:

git clone https://github.com/collinswamucii/library-management
cd library-management
Install and Configure MySQL:

    Install MySQL if not already installed.
    Create a database named library:
    sql

CREATE DATABASE library;
Update the application.properties file in src/main/resources/ with your MySQL credentials and JWT secret:
properties

    spring.datasource.url=jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=your_mysql_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update

    app.jwtSecret=yourSecretKey
    app.jwtExpirationMs=86400000
        Replace your_mysql_password with your MySQL password.
        Replace yourSecretKey with a secure, unique key for JWT signing (e.g., mySuperSecretKeyForJwt1234567890).

Build and Run the Application:
bash

    mvn clean install
    mvn spring-boot:run
        The application will start on http://localhost:8080.

API Endpoints
Authentication

    POST /auth/register - Register a new user
        Body:
        json

    {
        "username": "librarian",
        "password": "password123",
        "roles": ["LIBRARIAN"]
    }
    Response: 200 OK with user details.

POST /auth/login - Login and get JWT token

    Body:
    json

        {
            "username": "librarian",
            "password": "password123"
        }
        Response: 200 OK with JWT token (e.g., "eyJhbGciOiJIUzUxMiJ9...").

Books

    GET /books - Get all books (supports search and sort)
        Query Parameters:
            search (optional): Search by title or author (e.g., search=Spring).
            sortBy (default: title): Sort by field (e.g., sortBy=title or sortBy=publicationDate).
            order (default: asc): Sort order (e.g., order=asc or order=desc).
        Headers: Authorization: Bearer <jwt_token>
        Example: GET /books?search=Spring&sortBy=title&order=asc
        Response: 200 OK with list of books.
    GET /books/{id} - Get book by ID
        Headers: Authorization: Bearer <jwt_token>
        Example: GET /books/1
        Response: 200 OK with book details or 404 if not found.
    POST /books - Create a new book (LIBRARIAN only)
        Headers: Authorization: Bearer <jwt_token>
        Body:
        json

    {
        "title": "Spring in Action",
        "author": "Craig Walls",
        "isbn": "978-1617294945",
        "quantity": 5,
        "publicationDate": "2023-01-01"
    }
    Response: 200 OK with created book details or 403 if not a LIBRARIAN.

PUT /books/{id} - Update a book (LIBRARIAN only)

    Headers: Authorization: Bearer <jwt_token>
    Body:
    json

        {
            "title": "Spring in Action (Updated)",
            "author": "Craig Walls",
            "isbn": "978-1617294945",
            "quantity": 10,
            "publicationDate": "2023-01-01"
        }
        Example: PUT /books/1
        Response: 200 OK with updated book details or 403 if not a LIBRARIAN.
    DELETE /books/{id} - Delete a book (LIBRARIAN only)
        Headers: Authorization: Bearer <jwt_token>
        Example: DELETE /books/1
        Response: 204 No Content or 403 if not a LIBRARIAN.

Borrowing

    POST /borrow - Borrow a book
        Headers: Authorization: Bearer <jwt_token>
        Body:
        json

    {
        "bookId": 1,
        "borrowerName": "John Smith",
        "borrowDate": "2025-04-28"
    }
    Response: 200 OK with borrow details or 400 if no copies are available.

POST /return - Return a book

    Headers: Authorization: Bearer <jwt_token>
    Body:
    json

        {
            "bookId": 1,
            "borrowerName": "John Smith",
            "borrowDate": "2025-04-28",
            "returnDate": "2025-04-29"
        }
        Response: 200 OK with return details.

Authentication

All endpoints except /auth/* require a JWT token in the Authorization header as Bearer <token>. Use the /auth/login endpoint to obtain a token after registering a user.
Testing with Postman

A Postman collection is available to test the APIs. Click the button below to import the collection into Postman:

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/34269371-badae052-1143-4653-b7f1-076ede034629?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D34269371-badae052-1143-4653-b7f1-076ede034629%26entityType%3Dcollection%26workspaceId%3D1729b7ac-325f-4554-8611-55f062bef728)

Steps:

    Click the "Run in Postman" button above.
    Import the collection into Postman.
    Set the jwt_token variable in Postman after logging in via the /auth/login endpoint.
    Test the endpoints using the pre-configured requests.

Example Usage

    Register a Librarian:
    bash

curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{"username": "librarian", "password": "password123", "roles": ["LIBRARIAN"]}'
Login to Get a JWT Token:
bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "librarian", "password": "password123"}'

    Copy the returned JWT token.

Add a Book (LIBRARIAN only):
bash
curl -X POST http://localhost:8080/books \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <your_jwt_token>" \
-d '{"title": "Spring in Action", "author": "Craig Walls", "isbn": "978-1617294945", "quantity": 5, "publicationDate": "2023-01-01"}'
Borrow a Book:
bash

    curl -X POST http://localhost:8080/borrow \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer <your_jwt_token>" \
    -d '{"bookId": 1, "borrowerName": "John Smith", "borrowDate": "2025-04-28"}'

Troubleshooting

    MySQL Connection Issues:
        Ensure MySQL is running and the library database exists.
        Verify the credentials in application.properties.
    JWT Errors:
        Ensure the app.jwtSecret in application.properties is set to a valid, secure key.
    403 Forbidden:
        Check if the user has the required role (e.g., LIBRARIAN for adding books).
    404 Not Found:
        Verify the book ID exists when retrieving, updating, or deleting books.