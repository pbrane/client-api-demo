# TAC Case and RMA Case API

Welcome to the **TAC Case and RMA Case API** documentation. This API enables application developers to manage **TAC (Technical Assistance Center) Cases**, **RMA (Return Merchandise Authorization) Cases**, and their associated **Attachments** and **Notes**. It is built using **Spring Boot**, **Spring Data JPA**, and **MapStruct** to ensure a robust and maintainable architecture.

---

## Table of Contents

1. [Overview](#overview)
2. [Technologies Used](#technologies-used)
3. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
    - [Running the Application](#running-the-application)
4. [API Endpoints](#api-endpoints)
    - [TAC Cases](#tac-cases)
    - [RMA Cases](#rma-cases)
    - [Attachments](#attachments)
    - [Notes](#notes)
5. [Data Models](#data-models)
    - [TacCaseEntity](#taccaseentity)
    - [RmaCaseEntity](#rmacaseentity)
    - [TacCaseAttachmentEntity](#taccaseattachmententity)
    - [TacCaseNoteEntity](#taccasenoteentity)
6. [Error Handling](#error-handling)
7. [Authentication & Authorization](#authentication--authorization)
8. [Testing](#testing)
9. [Contributing](#contributing)
10. [License](#license)
11. [Contact](#contact)

---

## Overview

The **TAC Case and RMA Case API** provides comprehensive endpoints to:

- **Manage TAC Cases**: Create, retrieve, update, and delete TAC Cases.
- **Manage RMA Cases**: Associate RMA Cases with TAC Cases and perform CRUD operations.
- **Handle Attachments**: Upload, download, and manage attachments related to TAC and RMA Cases.
- **Manage Notes**: Add, retrieve, update, and delete notes associated with TAC Cases.

This API follows RESTful principles, ensuring stateless and scalable interactions.

---

## Technologies Used

- **Java 21**: The primary programming language.
- **Spring Boot 3.3**: Framework for building the API.
- **Spring Data JPA**: For database interactions.
- **MapStruct**: For mapping between entities and DTOs.
- **Lombok**: To reduce boilerplate code.
- **H2 Database**: In-memory database for development and testing.
- **Swagger/OpenAPI**: For API documentation and exploration.
- **JUnit & Mockito**: For testing.

---

## Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.9** or higher
- **Git** (optional, for cloning the repository)

### Installation

1. **Clone the Repository**

    ```bash
    git clone https://github.com/pbrane/client-api-demo.git
    cd client-api-demo
    ```

2. **Build the Project**

    ```bash
    mvn clean install
    ```

    This command compiles the code, runs tests, and packages the application.

### Configuration

The application uses **application.properties** for configuration. Below are key configurations:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:taccasedb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# MapStruct Configuration
# (If any specific configurations are needed)

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

**Note:** For production environments, configure a persistent database (e.g., PostgreSQL, MySQL) and update the `spring.datasource` properties accordingly.

### Running the Application

1. **Start the Application**

    ```bash
    mvn spring-boot:run
    ```

    The application will start on `http://localhost:8080`.

2. **Access H2 Console (Development Only)**

    Navigate to `http://localhost:8080/h2-console` to access the H2 database console.

    - **JDBC URL:** `jdbc:h2:mem:taccasedb`
    - **Username:** `sa`
    - **Password:** *(leave blank)*

3. **Access Swagger UI**

    API documentation and exploration can be accessed at `http://localhost:8080/swagger-ui.html`.

---

## API Endpoints

The API is versioned as `v1`. All endpoints are prefixed with `/api/v1`.

### TAC Cases

#### 1. **Create a TAC Case**

- **Endpoint:** `POST /api/v1/tacCases`
- **Description:** Creates a new TAC Case.
- **Request Body:**

    ```json
    {
        "caseNumber": "TAC123456",
        "caseStatus": "OPEN",
        "rmaNeeded": true,
        "subject": "Issue with Product X",
        "problemDescription": "Detailed description of the problem.",
        "contactEmail": "customer@example.com",
        "productName": "Product X",
        "productSerialNumber": "SN123456789",
        "casePriority": "HIGH",
        "caseOwner": "John Doe",
        "businessImpact": "High",
        "accountNumber": "ACC987654321",
        "faultySerialNumber": "FSN123456789",
        "faultyPartNumber": "FPN123456789"
    }
    ```

- **Response:**
    - **Status Code:** `201 Created`
    - **Body:**

        ```json
        {
            "id": 1,
            "caseNumber": "TAC123456",
            "caseStatus": "OPEN",
            "rmaNeeded": true,
            "subject": "Issue with Product X",
            "problemDescription": "Detailed description of the problem.",
            "contactEmail": "customer@example.com",
            "productName": "Product X",
            "productSerialNumber": "SN123456789",
            "casePriority": "HIGH",
            "caseOwner": "John Doe",
            "businessImpact": "High",
            "accountNumber": "ACC987654321",
            "faultySerialNumber": "FSN123456789",
            "faultyPartNumber": "FPN123456789",
            "caseNoteCount": 0,
            "caseCreatedDate": "2024-10-12T10:00:00Z",
            "caseClosedDate": null
        }
        ```

#### 2. **Retrieve All TAC Cases**

- **Endpoint:** `GET /api/v1/tacCases`
- **Description:** Retrieves a list of all TAC Cases.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Array of `TacCaseResponseDto` objects.

#### 3. **Retrieve a Specific TAC Case**

- **Endpoint:** `GET /api/v1/tacCases/{id}`
- **Description:** Retrieves details of a specific TAC Case by ID.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** `TacCaseResponseDto` object.

#### 4. **Update a TAC Case**

- **Endpoint:** `PATCH /api/v1/tacCases/{id}`
- **Description:** Updates details of an existing TAC Case.
- **Request Body:** Similar to the Create endpoint with fields to be updated.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Updated `TacCaseResponseDto` object.

#### 5. **Delete a TAC Case**

- **Endpoint:** `DELETE /api/v1/tacCases/{id}`
- **Description:** Deletes a specific TAC Case by ID.
- **Response:**
    - **Status Code:** `204 No Content`

### RMA Cases

#### 1. **Create an RMA Case**

- **Endpoint:** `POST /api/v1/rmaCases/{id}
- **Description:** Creates a new RMA Case associated with a specific TAC Case.
- **Request Body:**

    ```json
    {
        "rmaNumber": "RMA123456",
        "rmaStatus": "PENDING",
        "issue": "Defective component",
        "returnDate": "2024-10-15T10:00:00Z"
    }
    ```

- **Response:**
    - **Status Code:** `201 Created`
    - **Body:** `RmaCaseResponseDto` object.

#### 2. **Retrieve All RMA Cases**

- **Endpoint:** `GET /api/v1/rmaCases`
- **Description:** Retrieves all RMA Cases associated with a specific TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Array of `RmaCaseResponseDto` objects.

#### 3. **Retrieve a Specific RMA Case**

- **Endpoint:** `GET /api/v1/rmaCases/{id}`
- **Description:** Retrieves details of a specific RMA Case by ID within a TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** `RmaCaseResponseDto` object.

#### 4. **Update an RMA Case**

- **Endpoint:** `PATCH /api/v1/rmaCases/{id}`
- **Description:** Updates details of an existing RMA Case.
- **Request Body:** Similar to the Create endpoint with fields to be updated.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Updated `RmaCaseResponseDto` object.

#### 5. **Delete an RMA Case**

- **Endpoint:** `DELETE /api/v1/rmaCases/{id}`
- **Description:** Deletes a specific RMA Case by ID within a TAC Case.
- **Response:**
    - **Status Code:** `204 No Content`

### Attachments

#### 1. **Upload an Attachment to a TAC Case**

- **Endpoint:** `POST /api/v1/tacCases/{tacCaseId}/attachments`
- **Description:** Uploads a new attachment to a specific TAC Case.
- **Request:** `multipart/form-data`
    - **file:** The file to upload.
    - **name:** (Optional) Name of the attachment.
    - **mimeType:** (Optional) MIME type of the attachment.
    - **description:** (Optional) Description of the attachment.
- **Response:**
    - **Status Code:** `201 Created`
    - **Body:** `TacCaseAttachmentResponseDto` object.

#### 2. **Download an Attachment from a TAC Case**

- **Endpoint:** `GET /api/v1/tacCases/{tacCaseId}/attachments/{attachmentId}/download`
- **Description:** Downloads a specific attachment from a TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Headers:**
        - `Content-Disposition: attachment; filename="filename.ext"`
        - `Content-Type: mime/type`
    - **Body:** Binary file data.

#### 3. **Retrieve All Attachments for a TAC Case**

- **Endpoint:** `GET /api/v1/tacCases/{id}/attachments`
- **Description:** Retrieves all attachments associated with a specific TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Array of `TacCaseAttachmentResponseDto` objects.

#### 4. **Delete an Attachment from a TAC Case**

- **Endpoint:** `DELETE /api/v1/tacCases/{tacCaseId}/attachments/{attachmentId}`
- **Description:** Deletes a specific attachment from a TAC Case.
- **Response:**
    - **Status Code:** `204 No Content`

### Notes

#### 1. **Add a Note to a TAC Case**

- **Endpoint:** `POST /api/v1/tacCases/{id}/notes`
- **Description:** Adds a new note to a specific TAC Case.
- **Request Body:**

    ```json
    {
        "author": "John Doe",
        "text": "Initial assessment completed."
    }
    ```

- **Response:**
    - **Status Code:** `201 Created`
    - **Body:** `TacCaseNoteResponseDto` object.

#### 2. **Retrieve All Notes for a TAC Case**

- **Endpoint:** `GET /api/v1/tacCases/{id}/notes`
- **Description:** Retrieves all notes associated with a specific TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Array of `TacCaseNoteResponseDto` objects.

#### 3. **Retrieve a Specific Note**

- **Endpoint:** `GET /api/v1/tacCases/{tacCaseId}/notes/{noteId}`
- **Description:** Retrieves details of a specific note by ID within a TAC Case.
- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** `TacCaseNoteResponseDto` object.

#### 4. **Update a Note**

- **Endpoint:** `PUT /api/v1/tacCases/{tacCaseId}/notes/{noteId}`
- **Description:** Updates an existing note within a TAC Case.
- **Request Body:**

    ```json
    {
        "author": "John Doe",
        "text": "Initial assessment completed. Awaiting parts."
    }
    ```

- **Response:**
    - **Status Code:** `200 OK`
    - **Body:** Updated `TacCaseNoteResponseDto` object.

#### 5. **Delete a Note**

- **Endpoint:** `DELETE /api/v1/tacCases/{tacCaseId}/notes/{noteId}`
- **Description:** Deletes a specific note from a TAC Case.
- **Response:**
    - **Status Code:** `204 No Content`

---

## Data Models

### TacCaseEntity

Represents a TAC (Technical Assistance Center) Case.

- **Fields:**
    - `id`: `Long` - Unique identifier.
    - `href`: `String` - Hyperlink reference.
    - `caseNumber`: `String` - Unique case number.
    - `caseStatus`: `CaseStatus` - Status of the case (e.g., OPEN, CLOSED).
    - `rmaNeeded`: `Boolean` - Indicates if RMA is needed.
    - `subject`: `String` - Subject of the case.
    - `relatedRmaCount`: `Integer` - Number of related RMA Cases.
    - `relatedDispatchCount`: `Integer` - Number of related Dispatches.
    - `problemDescription`: `String` - Detailed description of the problem.
    - `installationCountry`: `String` - Country where installation occurred.
    - `firstResponseDate`: `OffsetDateTime` - Date of the first response.
    - `customerTrackingNumber`: `String` - Customer's tracking number.
    - `contactEmail`: `String` - Contact email (mandatory).
    - `productName`: `String` - Name of the product.
    - `productSerialNumber`: `String` - Product's serial number.
    - `productFirmwareVersion`: `String` - Firmware version.
    - `productSoftwareVersion`: `String` - Software version.
    - `caseSolutionDescription`: `String` - Description of the solution.
    - `casePriority`: `CasePriorityEnum` - Priority level (e.g., LOW, MEDIUM, HIGH).
    - `caseOwner`: `String` - Owner of the case.
    - `caseNoteCount`: `Integer` - Number of associated notes.
    - `caseCreatedDate`: `OffsetDateTime` - Date the case was created.
    - `caseClosedDate`: `OffsetDateTime` - Date the case was closed.
    - `businessImpact`: `String` - Impact on business.
    - `accountNumber`: `String` - Account number.
    - `faultySerialNumber`: `String` - Faulty serial number.
    - `faultyPartNumber`: `String` - Faulty part number.
    - `rmaCases`: `List<RmaCaseEntity>` - Associated RMA Cases.
    - `attachments`: `List<TacCaseAttachmentEntity>` - Associated Attachments.
    - `notes`: `List<TacCaseNoteEntity>` - Associated Notes.

- **Relationships:**
    - **OneToMany** with `RmaCaseEntity`.
    - **OneToMany** with `TacCaseAttachmentEntity`.
    - **OneToMany** with `TacCaseNoteEntity`.

### RmaCaseEntity

Represents an RMA (Return Merchandise Authorization) Case.

- **Fields:** *(Assuming based on context; adjust as per actual implementation)*
    - `id`: `Long`
    - `rmaNumber`: `String`
    - `rmaStatus`: `String`
    - `issue`: `String`
    - `returnDate`: `OffsetDateTime`
    - `tacCase`: `TacCaseEntity`

- **Relationships:**
    - **ManyToOne** with `TacCaseEntity`.

### TacCaseAttachmentEntity

Represents an Attachment associated with a TAC Case.

- **Fields:**
    - `id`: `Long`
    - `name`: `String`
    - `mimeType`: `String`
    - `description`: `String`
    - `content`: `byte[]`
    - `tacCase`: `TacCaseEntity`

- **Relationships:**
    - **ManyToOne** with `TacCaseEntity`.

### TacCaseNoteEntity

Represents a Note associated with a TAC Case.

- **Fields:**
    - `id`: `Long`
    - `author`: `String`
    - `date`: `OffsetDateTime`
    - `text`: `String`
    - `tacCase`: `TacCaseEntity`

- **Relationships:**
    - **ManyToOne** with `TacCaseEntity`.

---

## Error Handling

The API employs a **Global Exception Handler** to manage errors consistently across all endpoints. Below are common error scenarios and their responses:

### 1. **Resource Not Found**

Occurs when a requested TAC Case, RMA Case, Attachment, or Note does not exist.

- **Status Code:** `404 Not Found`
- **Response Body:**

    ```json
    {
        "timestamp": "2024-10-12T14:05:00Z",
        "status": 404,
        "error": "Not Found",
        "message": "Resource not found with id 999",
        "path": "/api/v1/tacCases/1/notes/999"
    }
    ```

### 2. **Validation Errors**

Triggered when input data fails validation constraints.

- **Status Code:** `400 Bad Request`
- **Response Body:**

    ```json
    {
        "timestamp": "2024-10-12T14:00:00Z",
        "status": 400,
        "error": "Bad Request",
        "message": "Validation failed for one or more fields.",
        "errors": {
            "author": "Author must not be blank.",
            "text": "Text must not be blank."
        },
        "path": "/api/v1/tacCases/1/notes"
    }
    ```

### 3. **Data Integrity Violation**

Occurs when operations violate database constraints.

- **Status Code:** `409 Conflict`
- **Response Body:**

    ```json
    {
        "timestamp": "2024-10-12T14:10:00Z",
        "status": 409,
        "error": "Data Integrity Violation",
        "message": "Operation failed due to data integrity constraints.",
        "path": "/api/v1/tacCases/1/attachments"
    }
    ```

### 4. **Internal Server Error**

Handles unexpected server-side errors.

- **Status Code:** `500 Internal Server Error`
- **Response Body:**

    ```json
    {
        "timestamp": "2024-10-12T14:15:00Z",
        "status": 500,
        "error": "Internal Server Error",
        "message": "An unexpected error occurred.",
        "path": "/api/v1/tacCases/1/rmaCases"
    }
    ```

---

## Authentication & Authorization

**Note:** As per the current implementation, authentication and authorization are not explicitly handled. For production environments, it is **highly recommended** to secure the API using mechanisms such as:

- **JWT (JSON Web Tokens)**
- **OAuth2**
- **Spring Security**

Implementing security ensures that only authorized users can access and manipulate TAC and RMA Cases, Attachments, and Notes.

---

## Testing

Comprehensive testing ensures the reliability and correctness of the API. The project includes:

- **Unit Tests:** Using **JUnit** and **Mockito** to test service and repository layers.
- **Integration Tests:** Using **Spring Boot Test** and **MockMvc** to test controller endpoints.

### Running Tests

Execute the following command to run all tests:

```bash
mvn test
```

**Note:** Ensure that all tests pass before deploying the application.

---

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. **Fork the Repository**

    Click the "Fork" button on the repository page to create your own copy.

2. **Clone Your Fork**

    ```bash
    git clone https://github.com/pbrane/client-api-demo.git
    cd client-api-demo
    ```

3. **Create a New Branch**

    ```bash
    git checkout -b feature/your-feature-name
    ```

4. **Make Changes**

    Implement your feature or bug fix.

5. **Commit Your Changes**

    ```bash
    git commit -m "Add feature: Description of the feature"
    ```

6. **Push to Your Fork**

    ```bash
    git push origin feature/your-feature-name
    ```

7. **Create a Pull Request**

    Navigate to the original repository and click on "Compare & pull request". Provide a detailed description of your changes.

**Please ensure that your code adheres to the project's coding standards and includes relevant tests.**

---

## License

This project is licensed under the [Apache 2.0 License](LICENSE).

---

## Contact

For any questions or support, please contact:

- **Name:** David Hustace
- **Email:** david@beaconstrategists.com
- **GitHub:** [pbrane](https://github.com/pbrane)

---

# Appendix

## Sample cURL Commands

### 1. **Add a New Note to a TAC Case**

```bash
curl -X POST "http://localhost:8080/api/v1/tacCases/1/notes" \
     -H "Content-Type: application/json" \
     -d '{
           "author": "John Doe",
           "text": "Initial assessment completed."
         }'
```

### 2. **Retrieve All Notes for a TAC Case**

```bash
curl -X GET "http://localhost:8080/api/v1/tacCases/1/notes" \
     -H "Accept: application/json"
```

### 3. **Retrieve a Specific Note**

```bash
curl -X GET "http://localhost:8080/api/v1/tacCases/1/notes/1" \
     -H "Accept: application/json"
```

### 4. **Update a Specific Note**

```bash
curl -X PUT "http://localhost:8080/api/v1/tacCases/1/notes/1" \
     -H "Content-Type: application/json" \
     -d '{
           "author": "John Doe",
           "text": "Initial assessment completed. Awaiting parts."
         }'
```

### 5. **Delete a Specific Note**

```bash
curl -X DELETE "http://localhost:8080/api/v1/tacCases/1/notes/1"
```

### 6. **Add a New TAC Case**

```bash
curl -X POST "http://localhost:8080/api/v1/tacCases" \
     -H "Content-Type: application/json" \
     -d '{
           "caseNumber": "TAC123456",
           "caseStatus": "OPEN",
           "rmaNeeded": true,
           "subject": "Issue with Product X",
           "problemDescription": "Detailed description of the problem.",
           "contactEmail": "customer@example.com",
           "productName": "Product X",
           "productSerialNumber": "SN123456789",
           "casePriority": "HIGH",
           "caseOwner": "John Doe",
           "businessImpact": "High",
           "accountNumber": "ACC987654321",
           "faultySerialNumber": "FSN123456789",
           "faultyPartNumber": "FPN123456789"
         }'
```

### 7. **Upload an Attachment to a TAC Case**

```bash
curl -X POST "http://localhost:8080/api/v1/tacCases/1/attachments" \
     -H "Content-Type: multipart/form-data" \
     -F "file=@/absolute/path/to/facepalm.png" \
     -F "name=facepalm.png" \
     -F "mimeType=image/png" \
     -F "description=An image showing a facepalm gesture"
```

---

**Happy Coding! 🚀**