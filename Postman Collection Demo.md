# Using the `client-api-demo` Postman Collection

This document provides a step-by-step guide on how to use the provided Postman collection to interact with the `client-api-demo` Spring Boot application running on `localhost:8080`. The collection contains a series of HTTP requests that demonstrate various operations on Technical Assistance Center (TAC) cases, attachments, notes, and Return Merchandise Authorization (RMA) cases.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Setup Instructions](#setup-instructions)
    - [1. Run the `client-api-demo` Application](#1-run-the-client-api-demo-application)
    - [2. Import the Postman Collection](#2-import-the-postman-collection)
    - [3. Configure the `base_url` Variable](#3-configure-the-base_url-variable)
3. [Collection Overview](#collection-overview)
    - [TAC Cases](#tac-cases)
    - [Attachments](#attachments)
    - [Notes](#notes)
    - [RMA Cases](#rma-cases)
4. [Detailed Request Descriptions](#detailed-request-descriptions)
    - [1. List all TAC Cases](#1-list-all-tac-cases)
    - [2. Retrieve TAC Case 1](#2-retrieve-tac-case-1)
    - [... and so on for each request]
5. [Additional Notes](#additional-notes)

---

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 8 or higher**
- **Maven** (for building the Spring Boot application)
- **Postman** (for importing and executing the collection)

## Setup Instructions

### 1. Run the `client-api-demo` Application

1. **Clone or download the `client-api-demo` repository.**

2. **Build the application:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

   The application should now be running on `http://localhost:8080`.

### 2. Import the Postman Collection

1. **Open Postman.**

2. **Import the Collection:**

    - Click on the **Import** button located at the top-left corner.
    - Select the **File** tab and upload the provided `client-api-demo.postman_collection.json` file.
    - Click **Import** to add the collection to your workspace.

### 3. Configure the `base_url` Variable

1. **Access the Collection Variables:**

    - In Postman, go to the **Collections** tab.
    - Expand the imported `client-api-demo` collection.
    - Click on the **Variables** tab.

2. **Set the `base_url`:**

    - Locate the `base_url` variable.
    - Change its **Current Value** to:

      ```
      http://localhost:8080
      ```

    - Click **Save** to apply the changes.

## Collection Overview

The collection is organized to demonstrate CRUD (Create, Read, Update, Delete) operations on TAC cases and their related entities such as attachments and notes. It also covers interactions with RMA cases.

### TAC Cases

- **List all TAC Cases**
- **Retrieve a specific TAC Case**
- **Update a TAC Case**
- **Delete a TAC Case**

### Attachments

- **Add Attachments to a TAC Case**
- **List all Attachments for a TAC Case**
- **Retrieve an Attachment**

### Notes

- **Add Notes to a TAC Case**
- **List all Notes for a TAC Case**
- **Retrieve a Note**

### RMA Cases

- **List all RMA Cases**
- **Retrieve a specific RMA Case**

## Detailed Request Descriptions

Below are detailed descriptions of each request in the collection.

### 1. List all TAC Cases

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases`
- **Description:** Retrieves a list of all TAC cases available in the system.

### 2. Retrieve TAC Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1`
- **Description:** Retrieves the details of TAC Case with ID `1`.

### 3. Update Case Status for TAC Case 1

- **Method:** `PATCH`
- **Endpoint:** `/api/v1/tacCases/1`
- **Body:**

  ```json
  {
      "caseStatus": "closed"
  }
  ```

- **Description:** Updates the status of TAC Case `1` to `closed`.

### 4. Retrieve TAC Case 1 (See Change)

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1`
- **Description:** Retrieves TAC Case `1` to verify the status change made in the previous request.

### 5. Add Pizza Box Attachment to TAC Case 1

- **Method:** `POST`
- **Endpoint:** `/api/v1/tacCases/1/attachments`
- **Body:**

    - **form-data:**
        - `file`: (Select a file from your local machine)
        - `description`: "Pizza Box optical amplifier."

- **Description:** Adds an attachment to TAC Case `1` with a description.

### 6. Add ODM Attachment to TAC Case 1

- **Method:** `POST`
- **Endpoint:** `/api/v1/tacCases/1/attachments`
- **Body:**

    - **form-data:**
        - `file`: (Select a file from your local machine)
        - `description`: "Cost-effective OEM/ODM ROADM solutions..."

- **Description:** Adds another attachment to TAC Case `1`.

### 7. List all TAC Case 1 Attachments

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/attachments`
- **Description:** Retrieves all attachments associated with TAC Case `1`.

### 8. Retrieve ODM Attachment from TAC Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/attachments/1/download`
- **Description:** Downloads the ODM attachment from TAC Case `1`.

### 9. Retrieve Pizza Box Attachment from TAC Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/attachments/1/download`
- **Description:** Downloads the Pizza Box attachment from TAC Case `1`.

### 10. List all Notes for TAC Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/notes`
- **Description:** Retrieves all notes associated with TAC Case `1`.

### 11. Add Note from Frank to TAC Case 1

- **Method:** `POST`
- **Endpoint:** `/api/v1/tacCases/1/notes`
- **Body:**

    - **form-data:**
        - `author`: "Frank.Liu@molex.com"
        - `text`: "Hi Molex Team,\n\nMux to Mux port is dirty on optical card..."
        - `date`: "2024-10-14T13:51:26.502Z"

- **Description:** Adds a note from Frank to TAC Case `1`.

### 12. List all Notes for TAC Case 1 (After Adding Note)

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/notes`
- **Description:** Retrieves all notes to confirm the addition of Frank's note.

### 13. Retrieve Frank's Note 1 from TAC Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1/notes/1`
- **Description:** Retrieves the specific note added by Frank to TAC Case `1`.

### 14. List all RMA Cases

- **Method:** `GET`
- **Endpoint:** `/api/v1/rmaCases`
- **Description:** Retrieves a list of all RMA cases.

### 15. Retrieve RMA Case 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/rmaCases/1`
- **Description:** Retrieves the details of RMA Case `1`.

### 16. Retrieve TAC Case 1 Copy

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1`
- **Description:** Retrieves TAC Case `1` again.

### 17. Cascade Remove TAC Case 1

- **Method:** `DELETE`
- **Endpoint:** `/api/v1/tacCases/1`
- **Description:** Deletes TAC Case `1` and cascades the deletion to related entities.

### 18. Verify Cascade Delete of RMA 1

- **Method:** `GET`
- **Endpoint:** `/api/v1/rmaCases/1`
- **Description:** Attempts to retrieve RMA Case `1` to verify it was deleted.

### 19. Retrieve TAC Case 1 (Expect 404)

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/1`
- **Description:** Confirms that TAC Case `1` has been deleted by expecting a 404 response.

### 20. Retrieve TAC Case 3

- **Method:** `GET`
- **Endpoint:** `/api/v1/tacCases/3`
- **Description:** Retrieves TAC Case `3` to demonstrate retrieval of another case.

## Additional Notes

- **Error Handling:** The collection includes requests that expect error responses (e.g., retrieving a deleted TAC Case). Ensure to handle these responses appropriately.
- **Data Persistence:** The application should have a configured database for data persistence. By default, it may use an in-memory database like H2.
- **File Uploads:** When adding attachments, make sure to select actual files from your local machine in Postman.
- **Timestamps:** Adjust the `date` field in note creation to the current date and time if necessary.

---

By following this guide, you should be able to interact with the `client-api-demo` 
application using the provided Postman collection effectively. 

If you encounter any issues, ensure that the application is running correctly 
and that the `base_url` variable is set properly in Postman.