# Rest Library API with Spring Boot

A robust RESTful API for library management, built with **Java 21**, **Spring Boot 3.4**, and **MySQL**. This project demonstrates clean code practices, database normalization, and JPA/Hibernate relationships.

<h2> STATUS: WORK IN PROGRESS </h2>

## Technologies

- **Java 21**
- **Spring Boot 3.4**
- **Spring Data JPA** (Hibernate)
- **SpringDoc OpenAPI 3** (Swagger)
- **MySQL**
- **Maven**

## Documentation & API Testing

The API is fully documented using **Swagger UI**. Once the application is running, you can access the interactive documentation to test the endpoints:

* **URL:** `http://localhost:8080/swagger-ui/index.html`

> **Note:** The project uses a **Decoupled Documentation Architecture**. All Swagger annotations are kept in separate `ControllerDocs` interfaces to maintain the Controllers clean and focused on business logic.

## Key Features

- **Full CRUD**: Complete management for Books and Authors.
- **Bulk Operations**: Support for `POST /bulk` endpoints, allowing the creation of multiple records in a single transactional request.
- **Global Error Handling**: Custom exception handler providing consistent JSON error responses.
- **Decoupled Docs**: Professional separation between API code and OpenAPI metadata.

## Getting Started

### 1. Environment Configuration
To ensure security and flexibility, this project uses environment variables for database credentials.

1. Locate the `.env.example` file in the root directory.
2. Create a copy named `.env`.
3. Fill in your local MySQL credentials (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).

### 2. Recommended IDE Setup (IntelliJ IDEA)
To automatically load the `.env` variables, I highly recommend:

* **EnvFile Plugin**: Enables support for `.env` files in Run/Debug configurations.
    * *How to use:* `Edit Configurations` -> `EnvFile` tab -> Check "Enable EnvFile".

## Database Schema
The project currently implements the following structure:
* **Author**: Manages writer information (name, nationality, biography).
* **Book**: Manages book details (title, ISBN, price) with a **Many-to-One** relationship linking each book to its respective author.