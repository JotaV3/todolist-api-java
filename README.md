# To-Do List API

REST API para cadastro de usuários e gerenciamento de tarefas, construída com Java 25 e Spring Boot. O projeto utiliza Spring Security com JWT, Spring Data JPA e suporte aos bancos H2 e PostgreSQL.

> **Status:** projeto em desenvolvimento.

## 🚀 Aplicação online

A API está disponível em produção no Render:

**https://todolist-api-java-y415.onrender.com**

## Funcionalidades

- Registro de usuários com geração automática de token JWT.
- Consulta, atualização e exclusão do usuário autenticado.
- Senhas criptografadas antes da persistência.
- Gerenciamento de tarefas do usuário autenticado.
- Associação obrigatória entre tarefa e usuário.
- Validação de dados com Jakarta Bean Validation.
- Validação personalizada para impedir títulos duplicados por usuário.
- Status de tarefa representado por código nas requisições e nome nas respostas.
- DTOs separados para entrada e saída da API.
- Mapeamento entre entidades e DTOs com MapStruct.
- Tratamento centralizado de erros e exceções da aplicação.
- Deploy em produção utilizando Render e PostgreSQL.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Security
- OAuth2 Resource Server com JWT
- Spring Data JPA e Hibernate
- Jakarta Bean Validation
- MapStruct
- Lombok
- Maven
- H2 Database
- PostgreSQL JDBC Driver
- JUnit e Spring Test
- Docker
- Render

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/jotave/todolist/
│   │   ├── api/
│   │   │   ├── config/          # Segurança e configuração JWT
│   │   │   ├── controllers/     # Endpoints REST
│   │   │   ├── dtos/            # Objetos de requisição e resposta
│   │   │   ├── handlers/        # Tratamento de exceções
│   │   │   └── mappers/         # Conversão MapStruct
│   │   ├── application/
│   │   │   ├── services/        # Regras de negócio e autenticação
│   │   │   ├── validations/     # Validações específicas
│   │   │   └── exceptions/      # Exceções da aplicação
│   │   ├── infra/
│   │   │   └── repositories/    # Repositórios Spring Data JPA
│   │   └── model/entities/      # Entidades User e Task
│   └── resources/
│       ├── application-dev.yaml
│       └── application-test.yaml
└── test/java/com/jotave/todolist/
    ├── controllers/             # Testes dos endpoints
    └── services/validations/    # Testes das validações
```

## Executando localmente

### Pré-requisitos

- Git
- JDK 25

### Clonar

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

A API ficará disponível em `http://localhost:8080`.

## Configuração

O perfil de desenvolvimento usa um banco H2 persistido em arquivo:

```text
jdbc:h2:file:./data/todolist-db
```

O console do H2 fica disponível em:

```text
http://localhost:8080/h2-console
```

Credenciais configuradas:

| Configuração | Valor |
| --- | --- |
| JDBC URL | `jdbc:h2:file:./data/todolist-db` |
| Usuário | `sa` |
| Senha | `sa` |

Os testes usam H2 em memória e recriam o esquema automaticamente. A configuração JWT está em `src/main/resources/application-dev.yaml`:

```yaml
app:
  jwt:
    secret: local-development-only-jwt-secret-change-before-deploying
    expiration-seconds: 3600
```

> Altere o segredo JWT antes de publicar a aplicação.

## Deploy no Render

O projeto está preparado para deploy como serviço Docker no Render, utilizando PostgreSQL gerenciado.

Configure as seguintes variáveis de ambiente no Web Service do Render:

| Variável | Valor |
| --- | --- |
| `DB_HOST` | Host interno do PostgreSQL |
| `DB_PORT` | Porta do PostgreSQL, normalmente `5432` |
| `DB_NAME` | Nome do banco |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Segredo aleatório com pelo menos 32 bytes |

O Render fornece a variável `PORT` para o serviço web, e a aplicação a utiliza automaticamente. O Dockerfile ativa o perfil `prod`, desativa o console H2 e configura o PostgreSQL.

Gere um segredo seguro localmente com:

```bash
openssl rand -base64 32
```

Não coloque o valor de `JWT_SECRET` no código, no Git ou em logs.

O schema é atualizado pelo Hibernate (`ddl-auto: update`) para facilitar o primeiro deploy. Para uma aplicação com dados importantes, configure migrações versionadas antes de evoluir o schema.

## Autenticação

Todas as rotas, exceto `POST /auth/register`, exigem um Bearer Token.

```http
Authorization: Bearer <jwt>
```

### Registrar usuário

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

A resposta contém o token JWT, o tipo do token, o tempo de expiração e o nome do usuário.

## Endpoints

### Usuário autenticado

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/api/users/me` | Consulta os dados do usuário autenticado |
| `PUT` | `/api/users/me` | Atualiza os dados do usuário autenticado |
| `DELETE` | `/api/users/me` | Remove o usuário autenticado |

### Tarefas do usuário autenticado

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/api/tasks/me` | Lista todas as tarefas do usuário autenticado |
| `POST` | `/api/tasks/me` | Cria uma tarefa para o usuário autenticado |
| `PUT` | `/api/tasks/{id}/me` | Atualiza uma tarefa do usuário autenticado |
| `DELETE` | `/api/tasks/{id}/me` | Remove uma tarefa do usuário autenticado |

## Exemplos de tarefas

### Criar tarefa

```http
POST /api/tasks/me
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "title": "Estudar Java",
  "description": "Praticar Spring Boot",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2
}
```

O campo `dueDate` usa o formato `YYYY-MM-DD`. O título deve ser único entre as tarefas do mesmo usuário.

### Atualizar tarefa

```http
PUT /api/tasks/1/me
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "title": "Estudar Spring Boot",
  "description": "Construir uma API REST",
  "dueDate": "2026-12-31",
  "taskStatusCode": 2
}
```

### Status das tarefas

| Código | Status |
| --- | --- |
| `1` | `Completed` |
| `2` | `InProgress` |
| `3` | `Paused` |

## Testes

### Linux/macOS

```bash
./mvnw test
```

### Windows

```powershell
.\mvnw.cmd test
```

O projeto também executa testes e build pelo GitHub Actions em pushes para `main` e pull requests.

## CI/CD

A workflow `.github/workflows/ci.yml` usa Java Temurin 25 e executa:

```bash
./mvnw test
./mvnw package -DskipTests
```

## Desenvolvimento

- Convenções REST e códigos HTTP para criação e exclusão.
- Divisão da aplicação em camadas API, aplicação, infraestrutura e domínio.
- Uso de H2 em desenvolvimento e testes.
- PostgreSQL em produção.
- Testes de controllers, serviços e validações.

## Autor

Desenvolvido por [JotaV3](https://github.com/JotaV3).
