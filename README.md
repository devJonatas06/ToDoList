# Todo List Backend

## 1. Visão Geral

Este projeto é um **backend** construído com **Java 21** e **Spring Boot**, utilizando **Spring Security** para autenticação, **JWT** para gerenciamento de tokens e **PostgreSQL** como banco de dados.

O sistema gerencia **usuários, tarefas (Task) e etiquetas (Label)**, garantindo **autenticação segura** e **controle de acesso** baseado em usuário.


### Tecnologias utilizadas
**Linguagem & Frameworks**: Java 21, Spring Boot, Spring Security, JPA/Hibernate

**Banco de Dados**: PostgreSQL

**Infraestrutura**: Docker & Docker Compose

**Autenticação**: JWT (JSON Web Token)

**Deploy**: AWS EC2 (Free Tier)


## 2. Estrutura do Projeto

### 2.1 Pacotes principais

#### AuthBackEnd

- **Controller:** `AuthController` e `UserController` – Endpoints de autenticação e usuário
- **Domain:** `User` – Representação da entidade usuário
- **DTO:** `LoginRequestDto`, `RegisterRequestDto`, `ResponseDto` – Objetos de transferência de dados
- **Infra.Security:** Configurações de segurança, JWT, filtros e CORS
- **Repository:** `UserRepository` – Persistência de dados de usuários

#### TodoListBackend

- **Controller:** `TaskController` e `LabelController` – Endpoints para tarefas e etiquetas
- **DTO:** `TaskDTO`, `LabelDTO`, `SubTaskDTO`, `TaskMapper` – Mapeamento de entidades
- **Entity:** `Task`, `SubTask`, `Label`, `Priority`, `Status` – Entidades JPA
- **Repository:** `taskRepository`, `LabelRepository` – Acesso a dados
- **Service:** `TaskService` – Lógica de negócio

#### Docker

- **Dockerfile:** Container do backend
- **docker-compose.yml:** Orquestração do backend e banco de dados PostgreSQL

---

## 3. Funcionalidades

### 3.1 Autenticação (AuthBackEnd)

- **Login:** `POST /auth/login` – Autentica usuário e retorna token JWT
- **Registro:** `POST /auth/register` – Cria novo usuário, criptografa senha e retorna token
- **Validação de token:** `GET /auth/validate` – Verifica se o token JWT é válido

### 3.2 Segurança

- Spring Security com `UserDetailsService`
- Filtro de autenticação JWT (`SecurityFilter`)
- Senhas criptografadas com BCrypt

### 3.3 Usuário

- **GET /user** – Endpoint de teste, retorna `"sucesso"`
- Pode ser expandido para buscar dados do usuário autenticado

### 3.4 Tarefas (TodoListBackend)

- **CRUD completo de tarefas (Task)**
- Associação com **SubTasks** e **Labels**
- Filtros:
    - Por Label: `/tasks/labels/{id}`
    - Por Priority: `/tasks/priority/{priority}`
    - Por Status: `/tasks/status/{TaskStatus}`
- Paginação: `/tasks/page`

### 3.5 Labels

- **CRUD completo de etiquetas (Label)**
- Associação Many-to-Many com tarefas

---

## 4. Endpoints Principais

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST   | /auth/login | Autentica usuário e retorna JWT |
| POST   | /auth/register | Cria usuário e retorna JWT |
| GET    | /auth/validate | Valida token JWT |
| GET    | /user | Teste de acesso ao usuário autenticado |
| GET    | /tasks | Lista tarefas do usuário |
| POST   | /tasks | Cria nova tarefa |
| GET    | /tasks/page | Lista tarefas paginadas |
| DELETE | /tasks/delete/{id} | Deleta tarefa do usuário |
| GET    | /tasks/labels/{labelId} | Filtra tarefas por label |
| GET    | /tasks/priority/{priority} | Filtra tarefas por prioridade |
| GET    | /tasks/status/{TaskStatus} | Filtra tarefas por status |
| GET    | /labels | Lista todas as labels |
| POST   | /labels | Cria label |
| GET    | /labels/{id} | Busca label por ID |
| PUT    | /labels/{id} | Atualiza label |
| DELETE | /labels/{id} | Deleta label |

---

## 5. Pré-requisitos

- Java 21
- Maven
- Docker & Docker Compose
- PostgreSQL

---

## 6. Configurações do projeto

### 6.1 .env.example

```env
POSTGRES_DB=todolist_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=sua_senha_postgres_aqui

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/todolist_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=sua_senha_postgres_aqui

API_SECURITY_TOKEN_SECRET=sua_chave_secreta_super_longa_aqui
CORS_ALLOWED_ORIGINS=http://localhost:4200,http://front-end:80

6.2 Docker Compose

version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: todolist-db
    environment:
      POSTGRES_DB: ${POSTGRES_DB:todolist_db}
      POSTGRES_USER: ${POSTGRES_USER:postgres}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD:}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - todolist-network
    restart: unless-stopped

  back-end:
    build: ./backend
    container_name: todolist-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL:jdbc:postgresql://postgres:5432/todolist_db}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME:postgres}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD:}
      API_SECURITY_TOKEN_SECRET: ${API_SECURITY_TOKEN_SECRET:}
      CORS_ALLOWED_ORIGINS: ${CORS_ALLOWED_ORIGINS:http://front-end:80}
    depends_on:
      - postgres
    networks:
      - todolist-network
    restart: unless-stopped

volumes:
  postgres_data:

networks:
  todolist-network:
    driver: bridge
```
## 6.3 Dockerfile Backend

```FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Configurar encoding UTF-8
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

# Copiar o arquivo pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
COPY . .

# Instalar Maven e construir o projeto
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests -Dfile.encoding=UTF-8 && \
    apk del maven

# Expor a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
```

## 7. Execução com Docker

### Build e execução:

docker-compose up --build

### Backend disponível em:

http://localhost:8080

## Banco de dados PostgreSQL:

```
Host: localhost
Port: 5432
Database: todolist_db
User: postgres
Password: sua_senha_postgres_aqui
```
## 9. Deploy na AWS EC2

Este backend foi implantado em uma instância AWS EC2 (Free Tier, t2.micro) com Docker e Docker Compose.
<img width="1619" height="501" alt="image" src="https://github.com/user-attachments/assets/b3d8c4de-db38-4c46-b89b-7ed0f5c9cbf4" />
<img width="1102" height="501" alt="image" src="https://github.com/user-attachments/assets/c525d5c4-3202-44dd-a112-1138c99e1497" />



# 8. Notas

    Tokens JWT possuem validade de 2 horas

    Senhas são criptografadas usando BCrypt

    O sistema garante que usuários só possam manipular suas próprias tarefas

# Autor
www.linkedin.com/in/jonatadev
https://github.com/devJonatas06
