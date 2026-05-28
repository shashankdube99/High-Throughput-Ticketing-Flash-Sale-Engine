# Ticketing System

A Spring Boot-based microservices ticketing system that provides comprehensive ticket management capabilities including creating, tracking, and managing support tickets.

## Features

- **Ticket Management**: Create, read, update, and delete support tickets
- **User Management**: Manage system users with different roles (Admin, Support Agent, Customer)
- **Ticket Status Tracking**: Track ticket lifecycle (Open, In Progress, Resolved, Closed, Reopened)
- **Priority Levels**: Organize tickets by priority (Low, Medium, High, Critical)
- **RESTful API**: Comprehensive REST endpoints for all operations
- **Exception Handling**: Centralized global exception handling
- **Data Persistence**: JPA/Hibernate with H2 (development) or MySQL (production)
- **Validation**: Input validation using Jakarta validation framework

## Project Structure

```
ticketing-system/
├── src/
│   ├── main/
│   │   ├── java/com/ticketing/system/
│   │   │   ├── entity/           # JPA entities (Ticket, User)
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── service/          # Business logic layer
│   │   │   ├── controller/       # REST endpoints
│   │   │   ├── dto/              # Data transfer objects
│   │   │   ├── exception/        # Custom exceptions and handlers
│   │   │   ├── config/           # Application configuration
│   │   │   └── TicketingSystemApplication.java  # Main entry point
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ticketing/system/
└── pom.xml                        # Maven configuration
```

## Technologies Used

- **Java 17**: Modern Java features
- **Spring Boot 3.2.0**: Application framework
- **Spring Data JPA**: Data persistence
- **H2 Database**: In-memory database (development)
- **Lombok**: Reduce boilerplate code
- **Jakarta Validation**: Input validation
- **Maven**: Build and dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd Ticketing-system
```

2. Build the project:
```bash
mvn clean install
```

## Running the Application

### Using Maven:
```bash
mvn spring-boot:run
```

### Using Java:
```bash
java -jar target/ticketing-system-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Health Check
- `GET /api/v1/health` - Check application health

### Ticket Endpoints
- `POST /api/v1/tickets` - Create a new ticket
- `GET /api/v1/tickets` - Get all tickets
- `GET /api/v1/tickets/{id}` - Get ticket by ID
- `GET /api/v1/tickets/user/{userId}` - Get tickets by user ID
- `GET /api/v1/tickets/assigned/{userId}` - Get tickets assigned to user
- `GET /api/v1/tickets/status/{status}` - Get tickets by status
- `PUT /api/v1/tickets/{id}` - Update ticket
- `DELETE /api/v1/tickets/{id}` - Delete ticket

### User Endpoints
- `POST /api/v1/users` - Create a new user
- `GET /api/v1/users` - Get all users
- `GET /api/v1/users/{id}` - Get user by ID
- `GET /api/v1/users/username/{username}` - Get user by username
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

## Database Configuration

### Development (H2 - Default)
The application uses an in-memory H2 database by default. Access the H2 console at:
```
http://localhost:8080/h2-console
```

### Production (MySQL)
To use MySQL, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticketing_system
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

Then run the application.

## Building for Production

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the repository or contact the development team.
