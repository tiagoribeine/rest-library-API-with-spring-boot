# Rest Library API with Spring Boot

A robust RESTful API for library management, built with **Java 21**, **Spring Boot 3.4**, and **MySQL**. This project demonstrates clean code practices, database normalization, and JPA/Hibernate relationships.

<h2> STATUS: WORK IN PROGRESS </h2>
## Technologies

- **Java 21**
- **Spring Boot 3.4**
- **Spring Data JPA** (Hibernate)
- **MySQL**
- **Maven**

## Getting Started

### 1. Environment Configuration
To ensure security and flexibility, this project uses environment variables for database credentials.

1. Locate the `.env.example` file in the root directory.
2. Create a copy named `.env`.
3. Fill in your local MySQL credentials (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).

### 2. Recommended IDE Setup (IntelliJ IDEA)
To automatically load the `.env` variables when running the application, I highly recommend installing the following plugin:

* **EnvFile**: Enables support for `.env` files in Run/Debug configurations.
    * *How to use:* Go to `Edit Configurations` -> `EnvFile` tab -> Check "Enable EnvFile" and add your `.env` file path.

## Database Schema
The project currently implements the following structure:
* **Author**: Manages writer information (name, nationality, biography).
* **Book**: Manages book details (title, ISBN, price) with a **Many-to-One** relationship linking each book to its respective author.