# IARTS Backend

Spring Boot backend for the Integrated ICT Assets Request and Tracking System.

## Stack

- Java 17 target
- Spring Boot 3.5.0
- Spring Web REST APIs
- Spring Security with JWT
- Spring Data JPA
- MySQL Connector/J
- Maven Wrapper

## Main modules

- Authentication and role-based access control
- ICT asset requests and approvals
- QTS printer request routing flag
- Equipment, stock and assignment tracking
- Laptop policy acknowledgement
- Intra-building movement requests
- Permission-to-remove workflow
- Take-home workflow
- Damage and warranty handling
- Audit logging

## MySQL setup

Create or allow auto-creation of the database in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS iarts_db;
```

Update `src/main/resources/application.properties` with your local MySQL username and password.

## Run

Use JDK 17, then run:

```powershell
.\mvnw.cmd spring-boot:run
```

Default admin seed user:

- username: `admin`
- password: `Admin@123`

Change the seeded password and JWT secret before any real deployment.
