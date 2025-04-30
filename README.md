# BudgetTune - Personal Finance Tracker

![BudgetTune Logo](/src/main/resources/static/logo2.png)

**BudgetTune** is a full-stack web application for managing personal finances. Users can record, update, filter, and delete their income and expenses in an organized and intuitive interface.

## Tech Stack

- **Backend**: Spring Boot 3 (Spring MVC, Spring Security, Spring Data JPA)
- **Frontend**: Thymeleaf, Bootstrap 5
- **Database**: MySQL 8
- **Documentation**: Swagger / OpenAPI 3
- **Testing**: JUnit 5, Mockito

## Features

- **User Authentication & Authorization**

  - Secure login/register/logout functionality
  - Passwords hashed with BCrypt
  - Role-based access control (USER / ADMIN)

- **Transaction Management**

  - Create, edit, view, and delete income and expense transactions
  - Each transaction belongs to a specific authenticated user

- **Filtering & Searching**

  - Filter transactions by description, amount range, category, date range, type, and payment method

- **Custom Categories**

  - Users can select predefined categories or define their own custom category

- **Statistics Dashboard**

  - Displays total number of transactions
  - Calculates and displays total income, total expenses, and net balance

- **API Documentation (Swagger)**

  - Exposes a RESTful API for transactions and users
  - Available at `/swagger-ui.html`

- **Responsive Design**

  - Optimized for desktop and mobile
  - Clean and modern interface using Bootstrap

## Database Configuration

- Database name: `budgettune_db`
- Connection settings in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/budgettune_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Running the Application Locally

1. Make sure MySQL is installed and running.
2. Create the database manually:

```sql
CREATE DATABASE budgettune_db;
```

3. Clone the repository and navigate into the project folder.
4. Configure your own database credentials inside `application.properties`.
5. Run the application using your IDE or by executing:

```bash
./mvnw spring-boot:run
```

6. Visit:

- Web App: [http://localhost:8080/login](http://localhost:8080/login)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Default Roles

- New registered users automatically receive the "USER" role.
- (Optional) Admin functionality can be expanded in the future.

## Project Structure

```bash
budgettune/
├── src/main/java/gr/aueb/budgettune/
│   ├── controller/       # Web controllers (Thymeleaf views)
│   ├── api/              # REST API controllers (Swagger)
│   ├── dto/              # Data Transfer Objects
│   ├── mapper/           # DTO <-> Entity mappers
│   ├── model/            # JPA Entities
│   ├── repository/       # Spring Data Repositories
│   ├── service/          # Service Layer (business logic)
│   ├── exception/        # Custom Exceptions for error handling
│   ├── config/           # Security and general configuration
│   └── BudgettuneApplication.java  # Main Spring Boot class
├── src/main/resources/
│   ├── static/            # Static files (CSS, JS, Images)
│   ├── templates/         # Thymeleaf templates
│   └── application.properties
├── pom.xml                # Maven configuration
```

## Authors

- Evangelos Theodorakis
- Based on AUEB Full Stack Bootcamp 2025 project requirements

---

### Notes

- Future improvements can include recurring transactions, multiple wallets, budget planning, or RESTful API security enhancements.
- Deployment options: Render.com, Railway.app, AWS Elastic Beanstalk, etc.

