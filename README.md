# To-Do List API

A RESTful API for managing users and tasks, developed with Java and Spring Boot.

This project is part of my backend development journey, where I'm applying object-oriented programming, REST API design, database persistence, data validation, and layered architecture.

> **Project Status:** 🚧 Work in Progress

The API supports CRUD operations for users and tasks, uses DTOs to manage request and response data, and applies validation rules to incoming requests. I'm continuously improving its architecture and maintainability as I expand my backend development skills.

## 🚀 Technologies

* **Java** — Programming language
* **Spring Boot** — Application framework
* **Spring MVC** — REST API development
* **Spring Data JPA** — Database access and persistence
* **Hibernate** — Object-relational mapping (ORM)
* **Jakarta Bean Validation** — Request validation
* **Maven** — Dependency management and build automation
* **H2 Database** — In-memory database for development
* **PostgreSQL JDBC Driver** — PostgreSQL database connectivity

## ✨ Features

### User Management

* Retrieve all users.
* Retrieve a user by ID.
* Create new users.
* Update existing users.
* Delete users.

### Task Management

* Retrieve all tasks.
* Retrieve a task by ID.
* Create tasks associated with existing users.
* Update task information while preserving the existing user association.
* Delete tasks.
* Manage task statuses using a Java enum.

### Architecture and Data Handling

* Layered architecture separating resources, services, repositories, and entities.
* Request and response DTOs to control the data accepted and returned by the API.
* Validation of request fields using Jakarta Bean Validation.
* Custom exceptions for resource-not-found and database-related errors.
* Appropriate HTTP response codes for common CRUD operations.
* Automatic generation of resource IDs through JPA identity generation.

## 🏗️ Project Architecture

The application follows a layered architecture to separate responsibilities and improve maintainability.

```text
src/
└── main/
    ├── java/
    │   └── com/jotave/todolist/
    │       ├── config/
    │       ├── dtos/
    │       │   ├── request/
    │       │   │   ├── TaskCreateRequestDTO.java
    │       │   │   ├── TaskUpdateRequestDTO.java
    │       │   │   └── UserRequestDTO.java
    │       │   └── response/
    │       │       ├── TaskResponseDTO.java
    │       │       └── UserResponseDTO.java
    │       ├── entities/
    │       │   └── enums/
    │       ├── repositories/
    │       ├── resources/
    │       └── services/
    │           └── exceptions/
    │
    └── resources/
        ├── application.properties
        └── application-test.properties
```

### Layer Responsibilities

* **Resources:** Handle HTTP requests and responses.
* **Services:** Implement business logic and coordinate application operations.
* **Repositories:** Provide database access through Spring Data JPA.
* **Entities:** Represent the application's persistent data model.
* **Request DTOs:** Define the data accepted by each API operation.
* **Response DTOs:** Define the data returned to API clients.
* **Enums:** Represent the available task statuses.
* **Exceptions:** Provide custom exception types for error handling.
* **Config:** Configure application components and development data.

## 📌 API Endpoints

The API runs locally at `http://localhost:8080` by default.

### Users

| Method   | Endpoint      | Description             |
| -------- | ------------- | ----------------------- |
| `GET`    | `/users`      | Retrieve all users      |
| `GET`    | `/users/{id}` | Retrieve a user by ID   |
| `POST`   | `/users`      | Create a new user       |
| `PUT`    | `/users/{id}` | Update an existing user |
| `DELETE` | `/users/{id}` | Delete a user           |

### Tasks

| Method   | Endpoint      | Description             |
| -------- | ------------- | ----------------------- |
| `GET`    | `/tasks`      | Retrieve all tasks      |
| `GET`    | `/tasks/{id}` | Retrieve a task by ID   |
| `POST`   | `/tasks`      | Create a new task       |
| `PUT`    | `/tasks/{id}` | Update an existing task |
| `DELETE` | `/tasks/{id}` | Delete a task           |

## 📝 Example Requests

The following examples illustrate the request formats accepted by the current API implementation.

### 1. Create a User

**POST** `/users`

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "5551234567",
  "password": "example-password"
}
```

The user's ID is generated automatically.

The API validates the request fields, including the name, email, phone number, and password length.

### 2. Create a Task

**POST** `/tasks`

```json
{
  "title": "Study Java",
  "description": "Practice Spring Boot and REST APIs",
  "taskStatusCode": 2,
  "userId": 1
}
```

The `userId` field identifies an existing user to associate with the task.

The application retrieves the specified user and establishes the relationship before saving the task.

If the specified user does not exist, the operation cannot be completed successfully.

### 3. Update a Task

**PUT** `/tasks/1`

```json
{
  "title": "Study Spring Boot",
  "description": "Practice building REST APIs",
  "taskStatusCode": 2
}
```

The task ID is provided through the URL.

The request uses `TaskUpdateRequestDTO`, which does not include `userId`. Updating a task therefore modifies its title, description, and status while preserving its existing user association.

### 4. Retrieve a Task

**GET** `/tasks/1`

Example response:

```json
{
  "id": 1,
  "title": "Study Spring Boot",
  "description": "Practice building REST APIs",
  "taskStatus": "InProgress",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "5551234567"
  }
}
```

The response uses `TaskResponseDTO` and includes information about the associated user without exposing the user's password.

### 5. Task Status

Tasks use a Java enum called `TaskStatus`.

| Code | Status     |
| ---- | ---------- |
| `1`  | Completed  |
| `2`  | InProgress |
| `3`  | Paused     |

Task creation and update requests use the numeric `taskStatusCode`. Task responses expose the corresponding status name through the `taskStatus` field.

## ✅ Request Validation

The API uses Jakarta Bean Validation to define rules for incoming request data.

Examples of the implemented validation constraints include:

* `@NotBlank` — Rejects missing or blank text fields.
* `@Email` — Checks the email format.
* `@Size` — Defines minimum or maximum field lengths.
* `@Min` and `@Max` — Restrict numeric values, including task status codes.
* `@Positive` — Requires a positive user ID.
* `@Valid` — Triggers validation of request DTOs in the controllers.

For example, a request containing a blank task title or an invalid email format should be rejected when it violates the corresponding validation rules.

## ⚙️ Running the Project

### Prerequisites

* Git
* A JDK compatible with the Java version configured in `pom.xml`

### 1. Clone the Repository

```bash
git clone https://github.com/JotaV3/todolist-api-java.git
```

### 2. Navigate to the Project Directory

```bash
cd todolist-api-java
```

### 3. Run the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

Once the application starts, the API will be available at:

`http://localhost:8080`

You can test the endpoints using Postman, Insomnia, or another HTTP client.

## 🗄️ Database Configuration

The current development configuration uses an in-memory H2 database and activates the `test` Spring profile.

The default JDBC URL is:

`jdbc:h2:mem:test`

The H2 Console is available at:

`http://localhost:8080/h2-console`

Use the following connection settings:

| Setting  | Value              |
| -------- | ------------------ |
| JDBC URL | `jdbc:h2:mem:test` |
| Username | `sa`               |
| Password | Leave empty        |

Because the database is in memory, its data is not preserved when the application stops.

The project also includes the PostgreSQL JDBC driver for PostgreSQL connectivity. The default configuration currently uses H2.

## 🔧 Planned Improvements

The project is still under development. Future improvements may include:

* Centralized exception handling and standardized API error responses.
* Secure password hashing and authentication.
* Expanded automated test coverage.
* Improved database configuration for different environments.
* Additional validation and refinement of business rules.
* Further improvements to code organization and maintainability.

## 🎯 Learning Goals

This project helps me strengthen my backend development skills through practical implementation.

Key areas of learning include:

* RESTful API design and HTTP methods.
* Object-oriented programming with Java.
* Database relationships and persistence with JPA.
* Separation of responsibilities through layered architecture.
* Request and response DTOs.
* Input validation and exception handling.
* Data mapping between DTOs and entities.
* Writing maintainable and well-organized code.

The project will continue to evolve as I implement new features and improve the existing architecture.

---

**Developer:** [JotaV3](https://github.com/JotaV3)