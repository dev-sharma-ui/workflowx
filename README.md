# WorkFlowX - Enterprise Workflow & Approval Management System

## Overview

WorkFlowX is an enterprise-grade workflow and approval management platform designed to streamline organizational request handling through secure, role-based workflows.

The platform enables employees to submit operational requests such as Leave, Work From Home, Travel, and Equipment requests while allowing managers to review, approve, reject, and track requests through a controlled approval lifecycle.

The project is built using modern backend engineering practices with Java Spring Boot, PostgreSQL, JWT Authentication, Role-Based Access Control (RBAC), and a layered architecture designed for scalability, maintainability, and future microservice decomposition.

---

## Business Problem

Many organizations still rely on emails, spreadsheets, and manual communication channels for approval workflows.

These approaches create several problems:

* Lack of centralized request tracking
* Poor visibility into approval status
* Unauthorized access to sensitive requests
* Missing audit trails
* Inefficient approval processes
* Difficulty scaling workflow operations

WorkFlowX addresses these challenges by providing a centralized and secure workflow management platform.

---

## Solution

WorkFlowX provides:

### Employee Portal

Employees can:

* Submit requests
* Track request status
* View request history
* Cancel pending requests

### Manager Portal

Managers can:

* Review requests
* Approve requests
* Reject requests
* Provide approval comments

### Administration

Administrators can:

* Manage platform access
* Monitor workflows
* Audit workflow activities
* Access organizational analytics

---

## Key Features

### Authentication & Security

* JWT Authentication
* Spring Security Integration
* BCrypt Password Encryption
* Stateless Authentication
* Role-Based Access Control (RBAC)

### User Roles

| Role     | Permissions                          |
| -------- | ------------------------------------ |
| EMPLOYEE | Create and manage personal requests  |
| MANAGER  | Review, approve, and reject requests |
| ADMIN    | Full system access                   |

---

### Request Management

Supported request types:

* Leave Request
* Work From Home Request
* Travel Request
* Equipment Request

Workflow lifecycle:

```text
PENDING
   ↓
APPROVED
   OR
REJECTED
   OR
CANCELLED
```

---

### Approval Workflow

```text
Employee
    ↓
Submit Request
    ↓
Status = PENDING
    ↓
Manager Review
    ↓
Approve / Reject
    ↓
Final Status Recorded
```

---

## Technology Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Security

* JWT (JSON Web Token)
* BCrypt Password Hashing

### Build Tool

* Maven

### Infrastructure

* Docker
* Docker Compose

### Development Tools

* Git
* GitHub
* Postman
* VS Code

---

## System Architecture

```text
Client
   ↓
REST API Layer
   ↓
Controller Layer
   ↓
Service Layer
   ↓
Repository Layer
   ↓
PostgreSQL Database
```

---

## Project Architecture

```text
src/main/java/com/workflowx

├── controller
│
├── dto
│   ├── auth
│   └── request
│
├── entity
│
├── enums
│
├── repository
│
├── security
│
├── service
│
├── util
│
└── config
```

### Layer Responsibilities

#### Controller Layer

Handles:

* HTTP Requests
* API Responses
* Request Validation

#### Service Layer

Contains:

* Business Logic
* Workflow Rules
* Authorization Rules

#### Repository Layer

Responsible for:

* Database Operations
* Data Retrieval
* Data Persistence

#### Security Layer

Responsible for:

* JWT Validation
* Authentication
* Authorization
* Security Context Management

---

## Database Design

### Users Table

Stores platform users.

Fields:

* id
* full_name
* email
* password
* role
* status
* created_at
* updated_at

---

### Requests Table

Stores workflow requests.

Fields:

* id
* title
* description
* request_type
* status
* employee_id
* submitted_at
* approved_at
* rejected_at
* manager_comments

---

### Entity Relationship

```text
User
  |
  | 1
  |
  | N
Request
```

One employee can create multiple requests.

---

## Security Architecture

### Authentication Flow

```text
User Login
    ↓
Credentials Validation
    ↓
JWT Generation
    ↓
Token Returned
    ↓
Protected API Access
```

---

### Authorization Flow

```text
JWT Token
    ↓
Role Extraction
    ↓
Security Context
    ↓
@PreAuthorize Validation
    ↓
Endpoint Access Decision
```

---

## Implemented REST APIs

### Authentication APIs

#### Register User

```http
POST /auth/register
```

#### Login User

```http
POST /auth/login
```

---

### Request APIs

#### Create Request

```http
POST /requests
```

#### Get My Requests

```http
GET /requests/my
```

#### Get Request By ID

```http
GET /requests/{requestId}
```

#### Approve Request

```http
PUT /requests/{requestId}/approve
```

#### Reject Request

```http
PUT /requests/{requestId}/reject
```

#### Cancel Request

```http
PUT /requests/{requestId}/cancel
```

---

## Sample Workflow

### Step 1

Employee logs in.

### Step 2

Employee submits:

```json
{
  "title": "Annual Leave Request",
  "description": "Need leave for family function",
  "requestType": "LEAVE"
}
```

### Step 3

Request status becomes:

```text
PENDING
```

### Step 4

Manager reviews request.

### Step 5

Manager approves request.

```json
{
  "managerComments": "Approved"
}
```

### Step 6

Final status:

```text
APPROVED
```

---

## Current Project Status

### Completed

* Infrastructure Setup
* PostgreSQL Integration
* Docker Configuration
* User Management
* JWT Authentication
* Role-Based Authorization
* Request Management
* Approval Workflow
* REST API Development
* Database Modeling

---

## Future Enhancements

### Audit Logging

Track:

* User actions
* Approval history
* Security events

### Notification Service

* Email Notifications
* In-App Notifications
* Approval Alerts

### Dashboard Analytics

* Request Statistics
* Approval Metrics
* Department Insights

### API Documentation

* Swagger / OpenAPI Integration

### Frontend Application

* React Dashboard
* Manager Portal
* Employee Portal

### Microservice Evolution

Potential future services:

```text
Authentication Service

Workflow Service

Notification Service

Audit Service

Analytics Service
```

---

## Learning Outcomes

This project demonstrates practical experience with:

* Enterprise Backend Development
* Java Spring Boot
* REST API Design
* PostgreSQL Database Design
* Authentication & Authorization
* Role-Based Access Control
* Layered Architecture
* Workflow Management Systems
* Dockerized Development
* Git-Based Collaboration

---

## Author

**Dev Sharma**

GitHub:
https://github.com/dev-sharma-ui

LinkedIn:
https://www.linkedin.com/in/dev-sharma-44b666298/

---

## License

This project is developed for educational, learning, and portfolio purposes.
