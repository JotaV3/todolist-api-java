# To-Do List API

A RESTful API for managing users and tasks, developed with Java and Spring Boot.

This project is part of my backend development journey, where I'm practicing REST API design, object-oriented programming, database persistence, and software development with the Java ecosystem.

> **Project Status:** 🚧 Work in Progress

New features and improvements are being implemented as the project evolves.

## 🚀 Technologies

* **Java** — Programming language
* **Spring Boot** — Application framework
* **Spring MVC** — REST API development
* **Spring Data JPA** — Database access and persistence
* **Hibernate** — ORM and entity management
* **Maven** — Dependency management and build automation
* **H2 Database** — In-memory database for development
* **PostgreSQL** — Database driver available for PostgreSQL integration

## ✨ Implemented Features

### User Management

* Retrieve all users.
* Retrieve a user by ID.
* Create new users.

### Task Management

* Retrieve all tasks.
* Retrieve a task by ID.
* Create tasks associated with existing users.
* Update task information.
* Delete tasks.

### Other Implementations

* Entity relationships between users and tasks.
* Task status management using Java enums.
* Custom exceptions for resource-not-found and database-related errors.
* Layered architecture separating resources, services, repositories, and entities.
* HTTP response status codes appropriate to common CRUD operations.

## 📌 API Endpoints

The API runs locally at `http://localhost:8080` by default.

### Users

| Method | Endpoint      | Description           |
| ------ | ------------- | --------------------- |
| GET    | `/users`      | Retrieve all users    |
| GET    | `/users/{id}` | Retrieve a user by ID |
| POST   | `/users`      | Create a new user     |

### Tasks

| Method | Endpoint      | Description             |
| ------ | ------------- | ----------------------- |
| GET    | `/tasks`      | Retrieve all tasks      |
| GET    | `/tasks/{id}` | Retrieve a task by ID   |
| POST   | `/tasks`      | Create a new task       |
| PUT    | `/tasks/{id}` | Update an existing task |
| DELETE | `/tasks/{id}` | Delete a task           |

## 📝 Example Requests

The examples below demonstrate the JSON payloads currently accepted by the API.

### Create a User

**POST** `/users`

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "5551234567",
  "password": "example-password"
}
```

### Create a Task

**POST** `/tasks`

```json
{
  "title": "Study Java",
  "description": "Practice Spring Boot and REST APIs",
  "taskStatusCode": 2,
  "user": {
    "id": 1
  }
}
```

The `user.id` field identifies an existing user to associate with the new task. The API checks whether the specified user exists before saving the task.

### Task Status

Tasks use an enum to represent their current status.

| Code | Status      |
| ---- | ----------- |
| `1`  | Completed   |
| `2`  | In Progress |
| `3`  | Paused      |

## 🏗️ Project Structure

The application follows a layered structure to separate responsibilities.

```text
src/
└── main/
    ├── java/
    │   └── com/jotave/todolist/
    │       ├── config/
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
* **Services:** Implement application logic.
* **Repositories:** Handle database operations through Spring Data JPA.
* **Entities:** Represent the application's persistent data model.
* **Enums:** Define task status values.
* **Exceptions:** Provide custom exception types for error conditions.
* **Config:** Configure application components and development data.

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

Once the application starts, the API is available at:

`http://localhost:8080`

### Database Configuration

The current default configuration uses an in-memory H2 database.

The application also includes sample users and tasks that are loaded when running with the `test` profile.

Because the database is in memory, the data is not preserved when the application stops.

The H2 Console is available at:

`http://localhost:8080/h2-console`

The default JDBC URL is:

`jdbc:h2:mem:test`

## 🔧 Planned Improvements

The project is still under development. Future improvements may include:

* Implementing dedicated request and response DTOs.
* Adding comprehensive request validation.
* Improving exception handling and API error responses.
* Implementing password hashing and authentication.
* Expanding automated test coverage.
* Improving database configuration for PostgreSQL.
* Expanding user management functionality.

## 🎯 Project Goals

The main goal of this project is to strengthen my backend development skills by building a REST API with Java and Spring Boot.

Throughout its development, I'm focusing on:

* RESTful API design.
* Object-oriented programming.
* Entity relationships and database persistence.
* Separation of responsibilities through a layered architecture.
* Exception handling.
* Clean and maintainable code.

This repository will continue to evolve as I implement new features and improve the application.

---

**Developer:** [JotaV3](https://github.com/JotaV3)
