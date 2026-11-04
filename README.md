GameFlix

Project Overview

GameFlix is a game subscription platform project developed using Spring Boot. The goal of this project is to create a backend that supports user account management, authentication, and protected user information.

This project focuses on implementing secure user registration and login using JWT authentication, along with a MySQL database to store user information.

Technologies Used

Java 21

Spring Boot

Maven

MySQL

Spring Security

JSON Web Tokens (JWT)

BCrypt password hashing

REST APIs

Features

User Registration: Allows new users to create an account.

Password Security: Uses BCrypt to hash passwords before storing them in the database.

User Login: Authenticates users and generates a JWT token.

Protected Profile: Allows authenticated users to access their profile information.

Token Validation: Rejects requests with missing, invalid, or modified JWT tokens.

Input Validation: Handles invalid registration information and duplicate accounts.

API Endpoints

Method

Endpoint

Description

POST

/api/auth/register

Creates a new user account

POST

/api/auth/login

Authenticates a user and returns a JWT

GET

/api/users/me

Returns the authenticated user's profile

Authentication

After logging in successfully, the user receives a JWT token. The token must be included in the request header when accessing protected endpoints.

Authorization: Bearer YOUR_JWT_TOKEN

The protected profile endpoint returns a successful response when a valid token is provided. Requests without a token or with an invalid or modified token are rejected.

Database

GameFlix uses MySQL with a database named gameflix. The users table stores user account information.

Passwords are hashed using BCrypt instead of being stored as plain text. Database credentials and JWT secrets should be kept private and should not be committed to GitHub.

How to Run the Project

Install Java 21, Maven, and MySQL.

Clone or download the project.

Create a MySQL database named gameflix.

Configure the database connection and JWT secret in the application's configuration.

Open the project in an IDE such as IntelliJ IDEA.

Run the Spring Boot application using the main application class or Maven.

Test the API endpoints using Postman or another API testing tool.

Make sure your MySQL server is running and your database configuration is correct before starting the application.

Testing

The main authentication features were tested to verify that the endpoints behave as expected.

Successful user registration.

Handling duplicate user accounts.

Successful login and JWT generation.

Accessing the protected profile endpoint with a valid token.

Rejecting requests without a token.

Rejecting requests with a modified or invalid token.

AI Usage

ChatGPT was used as a learning and development resource throughout this project. It helped explain Spring Boot and JWT concepts, troubleshoot errors, organize code, and prepare project documentation. I reviewed the suggestions and tested the application to understand how the different components work together. I used it to help with the authentication code and security configuration. One example was reviewing the password length validation. I changed the maximum password length to 64 characters and kept an additional check for BCrypt's 72-byte limit. This helps prevent passwords that exceed BCrypt's input limit while keeping the validation clear.

I reviewed and tested the code to make sure the application worked as expected.

Project Goal

The goal of GameFlix is to build a secure backend foundation for a game subscription platform. This project provides experience with Java development, REST APIs, database management, authentication, and application security.