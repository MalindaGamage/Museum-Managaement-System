# Museum Management System
## Project Overview
The Museum Management System is a Java-based application designed to manage various aspects of a museum, including collections, exhibitions, users, and visitors. The application leverages MySQL for database management and includes features for user authentication, CRUD operations, and data validation.

## Technologies Used
1. Java
2. MySQL
3. Maven
4. BCrypt (for password hashing)
5. Swing (for GUI)
6. UUID (for unique identifiers)
   
## Prerequisites
1. JDK 8 or higher
2. MySQL 5.7 or higher
3. Maven 3.6 or higher

## Installation
### Clone the Repository

1. `git clone https://github.com/MalindaGamage/MuseumManagaementSystem.git`
2. cd museum-management-system
3. Setup the Database
4. Create a database named museum_db.
5. Execute the create_tables.sql script to create the necessary tables.
6. Update the database connection details in src/main/java/database_connection/DatabaseConnection.java.
7. Build the Project

* `mvn clean install`
+ Run the Application
* `mvn exec:java -Dexec.mainClass="Main"`

## Project Structure
1. src/main/java
2. dao - Data Access Objects for interacting with the database.
3. model - Model classes representing the data.
4. service - Business logic and services.
5. ui - User Interface components using Swing.
6. database_connection - Database connection utility.

## Features
### User Management

1. Registration, login, and role-based access control.
2. Password hashing using BCrypt.
   
### Collection Management

1. CRUD operations for collections.
2. Input validation for attributes like name, description, category, acquisition date, status, and image URL.
   
### Exhibition Management

1. CRUD operations for exhibitions.
2. Input validation for attributes like title, start date, end date, description, and active status.
3. Ensures the start date is before the end date.

### Visitor Management

1. CRUD operations for visitors.
2. Input validation for attributes like full name, email, phone, visit date, and group size.
   
### Database Schema
Refer to the `mySQL.sql` file for the complete database schema.

### Dependencies

`<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.29</version>
    </dependency>
    <dependency>
        <groupId>at.favre.lib</groupId>
        <artifactId>bcrypt</artifactId>
        <version>0.9.0</version>
    </dependency>
    <dependency>
        <groupId>com.codenameone</groupId>
        <artifactId>codenameone-javase</artifactId>
        <version>7.0.148</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>`

### Contributing
* Contributions are welcome! Please open an issue or submit a pull request.

### License
* This project is licensed under the MIT License.

