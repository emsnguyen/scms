# Supply-Chain-Management-System

### Requirements

For building and running the application you need:

JDK 11

Maven 3

### Database configuration

Go to src/main/resources/application.properties and set your MySQL password
`spring.datasource.password={YourMysqlPassword}`

Change property to auto generate database schema
`spring.jpa.hibernate.ddl-auto=create`

Change property `spring.jpa.hibernate.ddl-auto=update` after the first run of project and update the schema

### Steps to run

1. Build the project using `mvn clean install`
2. Run using` mvn spring-boot:run`
3. The web application is accessible via localhost:8080

### Swagger UI link

`http://localhost:8080/swagger-ui.html`

### Link Documents

`https://drive.google.com/drive/folders/1pqmnvPBPG00OA1DmvYS4_eZKHljiZSOc?usp=sharing`


