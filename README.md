# To-Do List API

A REST API for user registration and task management, built with Java 25 and Spring Boot. The project uses Spring Security with JWT, Spring Data JPA, and support for H2 and PostgreSQL databases.

> **Status:** Work in progress.

## 🚀 Live application

The API is available in production on Render:

**https://todolist-api-java-y415.onrender.com**

## Features

- User registration with automatic JWT token generation.
- Authenticated user retrieval, update, and deletion.
- Password encryption before persistence.
- Task management for authenticated users.
- Mandatory association between each task and its user.
- Data validation with Jakarta Bean Validation.
- Custom validation to prevent duplicate task titles for the same user.
- Task status represented by a code in requests and a name in responses.
- Separate DTOs for API requests and responses.
- Entity-to-DTO mapping with MapStruct.
- Centralized application error and exception handling.
- Production deployment using Render and PostgreSQL.

## Technologies

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Security
- OAuth2 Resource Server with JWT
- Spring Data JPA and Hibernate
- Jakarta Bean Validation
- MapStruct
- Lombok
- Maven
- H2 Database
- PostgreSQL JDBC Driver
- JUnit and Spring Test
- Docker
- Render

## Project structure

```text
src/
├── main/
│   ├── java/com/jotave/todolist/
│   │   ├── api/
│   │   │   ├── config/          # Security and JWT configuration
│   │   │   ├── controllers/     # REST endpoints
│   │   │   ├── dtos/            # Request and response objects
│   │   │   ├── handlers/        # Exception handling
│   │   │   └── mappers/         # MapStruct conversions
│   │   ├── application/
│   │   │   ├── services/        # Business rules and authentication
│   │   │   ├── validations/     # Custom validations
│   │   │   └── exceptions/      # Application exceptions
│   │   ├── infra/
│   │   │   └── repositories/    # Spring Data JPA repositories
│   │   └── model/entities/      # User and Task entities
│   └── resources/
│       ├── application-dev.yaml
│       └── application-test.yaml
└── test/java/com/jotave/todolist/
    ├── controllers/             # Endpoint tests
    └── services/validations/    # Validation tests
```

## Running locally

### Prerequisites

- Git
- JDK 25

### Clone the repository

```bash
git clone https://github.com/JotaV3/todolist-api-java.git
cd todolist-api-java
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Configuration

The development profile uses an H2 database persisted to a file:

```text
jdbc:h2:file:./data/todolist-db
```

The H2 console is available at:

```text
http://localhost:8080/h2-console
```

Configured credentials:

| Configuration | Value |
| --- | --- |
| JDBC URL | `jdbc:h2:file:./data/todolist-db` |
| Username | `sa` |
| Password | `sa` |

Tests use an in-memory H2 database and recreate the schema automatically. JWT configuration is located in `src/main/resources/application-dev.yaml`:

```yaml
app:
  jwt:
    secret: local-development-only-jwt-secret-change-before-deploying
    expiration-seconds: 3600
```

> Change the JWT secret before publishing the application.

## Render deployment

The project is prepared for deployment as a Docker service on Render, using a managed PostgreSQL database.

Configure the following environment variables in the Render Web Service:

| Variable | Value |
| --- | --- |
| `DB_HOST` | Internal PostgreSQL host |
| `DB_PORT` | PostgreSQL port, usually `5432` |
| `DB_NAME` | Database name |
| `DB_USER` | Database user |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Random secret with at least 32 bytes |

Render provides the `PORT` environment variable to the web service, and the application uses it automatically. The Dockerfile activates the `prod` profile, disables the H2 console, and configures PostgreSQL.

Generate a secure secret locally with:

```bash
openssl rand -base64 32
```

Do not put the `JWT_SECRET` value in the source code, Git, or logs.

The schema is updated by Hibernate (`ddl-auto: update`) to simplify the initial deployment. For an application with important data, configure versioned migrations before evolving the schema.

## Authentication

All routes except `POST /auth/register` require a Bearer Token.

```http
Authorization: Bearer <jwt>
```

### Register a user

```http
POST /auth/register
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "5551234567",
  "password": "example-password"
}
```

The response contains the JWT token, token type, expiration time, and username.

## Endpoints

### Authenticated user

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/users/me` | Retrieves the authenticated user's data |
| `PUT` | `/api/users/me` | Updates the authenticated user's data |
| `DELETE` | `/api/users/me` | Deletes the authenticated user |

### Authenticated user's tasks

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/tasks/me` | Lists all tasks belonging to the authenticated user |
| `POST` | `/api/tasks/me` | Creates a task for the authenticated user |
| `PUT` | `/api/tasks/{id}/me` | Updates a task belonging to the authenticated user |
| `DELETE` | `/api/tasks/{id}/me` | Deletes a task belonging to the authenticated user |

## Task examples

### Create a task

```http
POST /api/tasks/me
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "title": "Study Java",
  "description": "Practice Spring Boot",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2
}
```

The `dueDate` field uses the `YYYY-MM-DD` format. The title must be unique among the user's tasks.

### Update a task

```http
PUT /api/tasks/1/me
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "title": "Study Spring Boot",
  "description": "Build a REST API",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2
}
```

### Task statuses

| Code | Status |
| --- | --- |
| `1` | `Completed` |
| `2` | `InProgress` |
| `3` | `Paused` |

## Tests

### Linux/macOS

```bash
./mvnw test
```

### Windows

```powershell
.\mvnw.cmd test
```

The project also runs tests and builds through GitHub Actions on pushes to `main` and pull requests.

## CI/CD

The `.github/workflows/ci.yml` workflow uses Java Temurin 25 and runs:

```bash
./mvnw test
./mvnw package -DskipTests
```

## Development

- REST conventions and HTTP status codes for creation and deletion.
- Application layers divided into API, application, infrastructure, and domain.
- H2 for development and testing.
- PostgreSQL in production.
- Controller, service, and validation tests.

## Author

Developed by [JotaV3](https://github.com/JotaV3).
