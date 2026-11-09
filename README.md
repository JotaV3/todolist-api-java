# To-Do List API

A RESTful API for managing users and their tasks, built with Java, Spring Boot, and Spring Data JPA.

This project is part of my backend development journey. It explores REST API design, data persistence, validation, DTOs, mapping, exception handling, and automated testing.

> **Project status:** Work in progress

## Features

### User management

- List all users and retrieve a user by ID.
- Create, update, and delete users.
- Validate user names, email addresses, phone numbers, and passwords.
- Return user data through a response DTO that does not expose the password.

### Task management

- List all tasks and retrieve a task by ID.
- Create, update, and delete tasks.
- Associate each task with an existing user.
- Store a task title, description, due date, and status.
- Prevent a user from creating tasks with duplicate titles.
- Check for duplicate titles when updating a task, while allowing a task to keep its own title.
- Preserve the task's user association when updating it.

### Validation and error handling

- Validate request data with Jakarta Bean Validation.
- Apply custom validation to task titles.
- Handle resource-not-found and database-related errors.
- Use request and response DTOs to define the API data contract.

## Technologies

- **Java**
- **Spring Boot**
- **Spring Web MVC**
- **Spring Data JPA** and **Hibernate**
- **Jakarta Bean Validation**
- **MapStruct**
- **Lombok**
- **Maven**
- **H2** for local development and tests
- **PostgreSQL JDBC driver** for PostgreSQL connectivity

## Project structure

```text
src/
├── main/
│   ├── java/com/jotave/todolist/
│   │   ├── controllers/       # HTTP endpoints
│   │   ├── dtos/              # Request and response DTOs
│   │   ├── entities/          # JPA entities and task status enum
│   │   ├── mappers/           # DTO/entity mapping with MapStruct
│   │   ├── repositories/      # Spring Data JPA repositories
│   │   └── services/          # Business logic, validation, and exceptions
│   └── resources/
│       ├── application.yaml
│       └── application-test.yaml
└── test/
    └── java/com/jotave/todolist/
        ├── controllers/
        └── services/validations/
```

## API

The application runs locally at `http://localhost:8080` by default.

### Users

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/users` | List all users |
| `GET` | `/users/{id}` | Retrieve a user |
| `POST` | `/users` | Create a user |
| `PUT` | `/users/{id}` | Update a user |
| `DELETE` | `/users/{id}` | Delete a user |

### Tasks

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/tasks` | List all tasks |
| `GET` | `/tasks/{id}` | Retrieve a task |
| `POST` | `/tasks` | Create a task |
| `PUT` | `/tasks/{id}` | Update a task |
| `DELETE` | `/tasks/{id}` | Delete a task |

Creating a user or task returns `201 Created` and a `Location` header. Successful deletions return `204 No Content`.

## Example requests

### Create a user

`POST /users`

```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "5551234567",
  "password": "example-password"
}
```

The response includes the generated user ID and public user fields; it does not include the password.

### Create a task

`POST /tasks`

```json
{
  "title": "Study Java",
  "description": "Practice Spring Boot",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2,
  "userId": 1
}
```

`dueDate` uses the `YYYY-MM-DD` format. The `userId` must refer to an existing user. Task titles must be unique for each user.

### Update a task

`PUT /tasks/1`

```json
{
  "title": "Study Spring Boot",
  "description": "Build a REST API",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2
}
```

The task ID is provided in the URL. The update request does not include a `userId`, so the existing user association is preserved.

### Retrieve a task

`GET /tasks/1`

Example response:

```json
{
  "id": 1,
  "title": "Study Spring Boot",
  "description": "Build a REST API",
  "dueDate": "2026-12-31",
  "taskStatus": "InProgress",
  "user": {
    "id": 1,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "phone": "5551234567"
  }
}
```

### Task status

Requests use the numeric `taskStatusCode`; responses include the corresponding status name.

| Code | Status |
| --- | --- |
| `1` | `Completed` |
| `2` | `InProgress` |
| `3` | `Paused` |

## Run locally

### Prerequisites

- Git
- A JDK compatible with the project's Maven configuration

### Clone the repository

```bash
git clone https://github.com/JotaV3/todolist-api-java.git
cd todolist-api-java
```

### Start the application

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Run the tests

On Linux or macOS:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

You can test the endpoints with Postman, Insomnia, or another HTTP client.

## Database

The default configuration uses a file-based H2 database:

```text
jdbc:h2:file:./data/todolist-db
```

The H2 Console is enabled at:

```text
http://localhost:8080/h2-console
```

Use the following connection settings:

| Setting | Value |
| --- | --- |
| JDBC URL | `jdbc:h2:file:./data/todolist-db` |
| Username | `sa` |
| Password | `sa` |

The test profile uses an in-memory H2 database and recreates the schema for tests. A PostgreSQL JDBC driver is included in the project, while H2 is used by the default application configuration.

##

**Developer:** [JotaV3](https://github.com/JotaV3)
