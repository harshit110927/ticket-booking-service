Multi-Tenant Ticket Booking Service
A robust, secure, and scalable backend for a multi-tenant ticket booking platform. This service is built with a layered monolithic architecture using Spring Boot, Java 24, and PostgreSQL. It features strict data isolation between tenants using database-level Row-Level Security (RLS).

This repository contains the complete backend service, database schema, and a single-file UI for comprehensive testing and demonstration.

✨ Core Features
Multi-Tenancy: Serve multiple tenants (e.g., different cinema chains) from a single application instance, with data strictly isolated.

Role-Based Access Control (RBAC): Differentiated permissions for ADMIN users (tenant managers) and USERs (customers).

Row-Level Security (RLS): An unbreakable, database-level security policy that prevents any possibility of cross-tenant data access.

Stateless JWT Authentication: Secure and scalable authentication suitable for modern distributed systems.

Complete Booking Workflow: Full API support for the entire lifecycle: from an admin setting up a venue and event to a customer booking a ticket.

Comprehensive Admin Panel: Endpoints for managing shows, venues (maps, sections, seats), pricing, and event generation.

🏛️ System Architecture
The backend is a layered monolithic service designed for a clear separation of concerns, making it maintainable and scalable.

Client (UI) -> Controller Layer -> Service Layer -> Repository Layer -> Database (PostgreSQL)

Controller Layer: Exposes RESTful API endpoints, handles HTTP requests, and performs initial validation.

Service Layer: Contains the core business logic and orchestrates operations.

Repository Layer: Manages data access and persistence using Spring Data JPA.

Database: PostgreSQL serves as the single source of truth, enforcing data integrity and security policies.

🛠️ Technology Stack
Framework: Spring Boot 3.5.4

Language: Java 24

Database: PostgreSQL 17.5

Data Access: Spring Data JPA / Hibernate

Security: Spring Security 6.5

Authentication: JSON Web Tokens (JWT)

Build Tool: Maven

🚀 Getting Started
Follow these instructions to get a local copy up and running for development and testing.

Prerequisites
Java JDK 24 or later

Apache Maven

PostgreSQL 17.5 or later

An IDE like IntelliJ IDEA or VS Code

1. Clone the Repository
git clone https://github.com/your-username/ticket-booking-service.git
cd ticket-booking-service

2. Database Setup
Create a PostgreSQL Database:

CREATE DATABASE ticket_booking_db;

Connect to the database using a tool like psql or pgAdmin.

Run the Schema Script: Execute the entire schema.sql file located in src/main/resources/ to create all tables, types, and security policies.

(Optional) Populate with Test Data: Execute the test_data.sql script to populate the database with a rich dataset for two tenants, enabling full testing of all features.

3. Configure the Application
Open the application.properties file located in src/main/resources/ and update the PostgreSQL connection details:

spring.datasource.url=jdbc:postgresql://localhost:5432/ticket_booking_db
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password

# JWT Secret Key (change this for production)
jwt.secret=your-super-secret-key-that-is-long-and-secure

4. Run the Application
You can run the service directly from your IDE by running the main application class or by using the Maven wrapper:

./mvnw spring-boot:run

The backend will start on http://localhost:8088.

🧪 How to Test
This project includes a single-file, dependency-free UI for testing all API endpoints.

Ensure the backend service is running.

Open the index.html file (located in the testing-ui directory) in any modern web browser.

The UI provides forms and buttons mapped to every API endpoint. Use the data from test_data.sql or create your own to test the functionalities.

All API responses are displayed in real-time for easy debugging.

🔐 Security Model
Security is a core principle of this application, enforced at multiple levels:

JWT Authentication: A signed JWT is required to access protected endpoints. The token contains the user's email, roles, and tenantId.

Spring Security Filters: All requests pass through a filter chain that validates the JWT and sets up the security context.

Role-Based Authorization: Endpoints are protected using method-level security (@PreAuthorize) or security filter chains, restricting access to users with the appropriate role (ADMIN or USER).

Row-Level Security (RLS): This is the final and most powerful security layer. A custom filter reads the tenantId from the JWT and sets it as a session variable in PostgreSQL. Database policies then automatically filter every query, making it impossible for one tenant to access another's data.

🗺️ API Endpoints
The API is divided into three categories. The base URL is http://localhost:8088/api/v1.

Public Endpoints
Method

Endpoint

Description

POST

/onboarding/tenant

Creates a new Tenant and its first ADMIN.

POST

/auth/register

Registers a new customer (USER role).

POST

/auth/login

Logs in any user and returns a JWT.

GET

/events/{eventId}/tickets

Fetches the seat map for a specific event.

Admin-Only Endpoints (ADMIN Role Required)
Method

Endpoint

Description

POST

/shows

Creates a new show template.

POST

/venue/maps

Creates a new venue map for the tenant.

POST

/venue/sections

Creates a new section within a map.

POST

/venue/seats

Creates a new seat within a section.

POST

/venue/pricing-tiers

Creates a new pricing tier for the tenant.

POST

/events

Creates a new event for the tenant.

POST

/events/pricing

Sets the price for a tier at an event.

POST

/events/{eventId}/generate-tickets

Generates all tickets for an event.

Customer-Only Endpoints (USER Role Required)
Method

Endpoint

Description

POST

/bookings/initiate

Initiates the booking process.

🌐 Future Work
The current monolithic architecture is robust but can be evolved. The next major step is to refactor this service into a microservices architecture to improve scalability, fault tolerance, and independent deployability. The planned services include:

Admin Service: For all administrative tasks.

Booking Service: For all customer-facing booking operations.

API Gateway: A single entry point for all requests.

Service Registry: For service discovery.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.
