# Ticketing System - Copilot Instructions

## Project Overview

This is a Spring Boot-based microservices ticketing system. The project uses Maven for dependency management and provides comprehensive REST APIs for ticket and user management.

## Development Guidelines

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable and method names
- Add JavaDoc comments for public classes and methods
- Use Lombok annotations to reduce boilerplate code

### Project Structure
- **entity**: JPA entities representing database tables
- **repository**: Spring Data JPA repositories for data access
- **service**: Business logic and transaction management
- **controller**: REST API endpoints
- **dto**: Data transfer objects for API requests/responses
- **exception**: Custom exceptions and global exception handler
- **config**: Application configuration classes

### Dependencies
- Spring Boot 3.2.0
- Spring Data JPA
- Lombok
- H2 Database (development)
- Jakarta Validation

### Building and Running

Build:
```bash
mvn clean install
```

Run:
```bash
mvn spring-boot:run
```

### Database Configuration

**Development**: H2 in-memory database (default)
- Access console: http://localhost:8080/h2-console
- URL: jdbc:h2:mem:ticketdb
- Username: sa
- Password: (empty)

**Production**: MySQL (configure in application.properties)

### Testing

Run tests:
```bash
mvn test
```

### Common Tasks

#### Adding a New Entity
1. Create entity class in `src/main/java/com/ticketing/system/entity/`
2. Create repository in `src/main/java/com/ticketing/system/repository/`
3. Create DTO in `src/main/java/com/ticketing/system/dto/`
4. Create service in `src/main/java/com/ticketing/system/service/`
5. Create controller in `src/main/java/com/ticketing/system/controller/`

#### Adding a New API Endpoint
1. Add method to appropriate service class
2. Add corresponding endpoint method to controller
3. Add appropriate HTTP method annotation (@GetMapping, @PostMapping, etc.)
4. Return ResponseEntity with proper HTTP status

#### Configuring Database
- For H2: No configuration needed (default)
- For MySQL: Update `application.properties` with MySQL connection details

### API Documentation

All endpoints follow RESTful conventions:
- `GET` - Retrieve resources
- `POST` - Create resources
- `PUT` - Update resources
- `DELETE` - Delete resources

Base URL: `http://localhost:8080/api/v1/`

### Important Notes

- Always use `@Transactional` for methods that modify data
- Implement proper exception handling using GlobalExceptionHandler
- Use DTOs for API requests/responses instead of entities
- Validate user input using Jakarta validation annotations
- Use `@RequiredArgsConstructor` from Lombok for dependency injection

## Troubleshooting

### Common Issues

**Port already in use**:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

**Database connection issues**:
- Check application.properties database configuration
- Ensure MySQL is running (if using MySQL)
- Verify database credentials

**Maven build failures**:
```bash
mvn clean install -U
```

### Debug Mode

Run application in debug mode:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Jakarta EE Documentation](https://jakarta.ee/)
- [Lombok Documentation](https://projectlombok.org/)

## Additional Configuration

### Logging
Default logging level is INFO. Change in `application.properties`:
```properties
logging.level.com.ticketing.system=DEBUG
```

### H2 Console
Enabled by default in development. Disable in production:
```properties
spring.h2.console.enabled=false
```

### Actuator Endpoints
To add Spring Boot Actuator for monitoring, add dependency:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
