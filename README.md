## Project description
This repository represents the implementation of the project assignments developed during the course of Distributed Systems
at the Technical University of Cluj-Napoca. The project was split into 4 assignments with the end goal of implementing
a functioning online medical platform, featuring both a client and a server side. The main features of the project are:
 * A backend implemented in Java and Kotlin using the Spring Boot Framework.
 * A frontend implemented in Typescript using the React framework.
 * A PostgreSQL database manipulated from the backend using Hibernate.
 * The backend exposed its functionality through APIs defined as REST and SOAP services.
 * Integration of a live notification system to the UI using Web-Sockets.
 * Asynchronous communication between parts of the application using RabbitMQ.
 * Integration of additional functionalities using as an extra application using gRPC.


## Development Requirements
  * install **Java 8**
  * install **PostgreSQL** 
  * install **NodeJS and NPM**
  * install **Gradle**

# Database setup
Run the sql script from the **database setup** folder in order to create the needed database and tables

# Server start
  * Import each project (without the client) in Intellij using gradle configuration.
  * Run in terminal: 
       * **gradlew clean build**
  * Run the main function inside each project to start the corresponding services

# Client start
  * Import the client project in Webstorm
  * Run in terminal:
       * **npm install**
  * Run in terminal:
       * **npm run start**

# Documentation
For a comprehensive description of the project's architecture, together with the significant deployment and database diagrams,
please read the **Documentation** document.