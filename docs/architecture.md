# ShareSpace Architecture

## Architecture Pattern
This project follows a layered architecture:

Controller → Service → Repository → MongoDB

## Layer Responsibilities

### Controller Layer
Handles HTTP requests and responses.

Example:
- WorkspaceController

Responsibility:
- Accept API request
- Validate input
- Call service layer
- Return response

### Service Layer
Contains business logic.

Example:
- WorkspaceService

Responsibility:
- Process workspace creation logic
- Interact with repository

### Repository Layer
Interacts with MongoDB using Spring Data.

Example:
- WorkspaceRepository extends MongoRepository

### Model Layer
Represents MongoDB documents.

Examples:
- Workspace
- FileMeta