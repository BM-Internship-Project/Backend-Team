---

# Money Transfer Application

## Overview

This is a **Money Transfer Application** that allows users to transfer money between accounts, view account balances, and manage favorite recipients. The application uses Java Spring Boot for the backend, with PostgreSQL as the database, and Redis for caching. It is containerized using Docker and deployed on Railway.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Documentation](#api-documentation)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)

## Features
- **User registration and login**: Users can sign up and log in securely using email and password.
- **Money Transfer**: Users can transfer funds between accounts.
- **Favorite Recipients**: Add and manage favorite recipients for quick and easy transfers.
- **Transactions**: View transaction history.
- **Authentication**: Secure login, token-based authentication, and logout functionality.
- **Rate Limiting**: Uses the Token Bucket algorithm to limit the number of requests a user can make in a given period.
- **Caching**: Improves performance by using Redis for caching frequently accessed data.
- **Security**: Special attention to secure transactions and data handling, with token-based authentication and rate-limiting mechanisms in place.

## Technologies Used
- **Java 17**: The programming language used for the backend.
- **Spring Boot 3:**
    - Spring Security 6 for authentication and authorization
    - Spring Web for building RESTful APIs
- **PostgreSQL**: Relational database used to persist account and transaction data.
- **Redis**: Caching layer used for improving performance.
- **Maven**: Build tool for compiling and packaging the application.
- **JWT Token**: For secure token-based authentication.
- **Token Bucket Algorithm**: For rate-limiting requests to prevent abuse.
- **Postman**: API documentation and testing.
- **Docker**: For containerizing the application for easy deployment.
- **Railway**: Platform used for deploying the application.

## API Documentation
- [Postman API Documentation](https://documenter.getpostman.com/view/31979113/2sAXjSyoUB)

## Installation and Setup

### Prerequisites
- **[Java 17](https://www.oracle.com/java/technologies/downloads)** or higher
- **[Maven 3.9.9](https://maven.apache.org/download.cgi)** or higher
- **[PostgreSQL](https://www.postgresql.org/download/)** database
- **[Redis](https://redis.io/downloads/)** server

### Local Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/BM-Internship-Project/Backend-Team.git
    ```

2. **Navigate to the project directory:**
    ```bash
    cd money-transfer-application
    ```

3. **Install dependencies:**
    - Ensure you have [Java](https://www.oracle.com/java/technologies/downloads) and [Maven](https://maven.apache.org/download.cgi) installed. Then, run:
        ```bash
        mvn install
        ```
4. **Configure application properties:**
    - Edit `src/main/resources/application.properties` to set up your database and other configuration settings.

5. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

## Deployment
The application is deployed on Railway.

---
