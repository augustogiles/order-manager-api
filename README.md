
# Order Manager System

A Spring Boot application for managing orders and stock movements. The system automatically fulfills orders when stock is available and sends email notifications when orders are completed.

## Prerequisites

- Java 8
- Maven 3.6+
- PostgreSQL 12+
- Git

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd order-manager
```

2. Configure the database:
    - Create a PostgreSQL database named `ordermanager`
    - Update the database credentials in `src/main/resources/application.properties` if needed:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/ordermanager
      spring.datasource.username=postgres
      spring.datasource.password=postgres
      ```

3. Configure email settings:
    - Update the email configuration in `src/main/resources/application.properties`:
      ```properties
      spring.mail.host=smtp.gmail.com
      spring.mail.port=587
      spring.mail.username=your-email@gmail.com
      spring.mail.password=your-app-password
      ```

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring:boot run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create a new user
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com"
  }
  ```
- `PUT /api/users/{id}` - Update user
  ```json
  {
    "name": "John Doe Updated",
    "email": "john.updated@example.com"
  }
  ```
- `DELETE /api/users/{id}` - Delete user

### Items

- `GET /api/items` - Get all items
- `GET /api/items/{id}` - Get item by ID
- `POST /api/items` - Create a new item
  ```json
  {
    "name": "Product Name"
  }
  ```
- `PUT /api/items/{id}` - Update item
  ```json
  {
    "name": "Updated Product Name"
  }
  ```
- `DELETE /api/items/{id}` - Delete item

### Orders

- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create a new order
  ```json
  {
    "item_id": 1,
    "user_id": 1,
    "quantity": 5
  }
  ```
- `GET /api/orders/user/{user_id}` - Get all orders for a specific user

### Stock Movements

- `GET /api/stock/movements` - Get all stock movements
- `GET /api/stock/movements/{id}` - Get stock movement by ID
- `POST /api/stock/movements` - Create a new stock movement
  ```json
  {
    "item_id": 1,
    "quantity": 10
  }
  ```
- `GET /api/stock/movements/item/{item_id}` - Get all stock movements for a specific item

## Features

- Automatic order fulfillment when stock is available
- Email notifications for completed orders
- Stock movement tracking
- Order completion status tracking
- Comprehensive logging of all operations
- Input validation
- Error handling

## Logging

The application logs are stored in the `logs` directory:
- `application.log` - General application logs
- `business.log` - Business operation logs (orders, stock movements, etc.)

## Error Handling

The API returns appropriate HTTP status codes:
- 200: Success
- 201: Created
- 400: Bad Request (validation errors)
- 404: Not Found
- 500: Internal Server Error

Error responses follow this format:
```json
{
  "status": 404,
  "message": "Resource not found"
}
```

## Testing the API

You can use tools like Postman or curl to test the API endpoints. Here's an example using curl:

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'

# Create an item
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Product"}'

# Create a stock movement
curl -X POST http://localhost:8080/api/stock/movements \
  -H "Content-Type: application/json" \
  -d '{"item_id":1,"quantity":10}'

# Create an order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"item_id":1,"user_id":1,"quantity":5}'
```
