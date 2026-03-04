# API Documentation

## Base URL

http://localhost:8080/api

## Create Workspace

### Endpoint

POST /workspace

### Request Parameters

| Parameter | Type   | Description    |
|-----------|--------|----------------|
| name      | String | Workspace name |

### Example Request

POST http://localhost:8080/api/workspace?name=StudyRoom

### Example Response

{
  "id": "65f34d3a",
  "name": "StudyRoom"
}

### Status Codes

- 200 OK → Workspace created
- 400 Bad Request → Invalid input