
# Real Time Ticket Management System

## Introduction
The **Ticket Management System** is a command-line interface (CLI) application that allows users to manage the release and purchase of tickets for events. The system supports dynamic configuration, ticket release at a specified rate, customer ticket retrieval, and various administrative operations such as starting and stopping the ticket release. The backend is built using **Spring Boot** and the **MVC architecture**. Data is persisted in a **MySQL database**, and the project leverages **Maven** for dependency management.

## Setup Instructions

### Prerequisites
Before you begin, ensure you have the following installed:
- **Java 17 or later**
  - You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use [OpenJDK](https://openjdk.java.net/).
- **H2** (for the database)
  - Download and install H2 Console from [H2's website](https://www.h2database.com/html/main.html).
- **Node.js** (for running the frontend, if applicable)
  - Download it from [Node.js website](https://nodejs.org/en/).
- **Maven** (for building the project)
  - If you don’t have Maven installed, follow the instructions on [Maven's website](https://maven.apache.org/install.html).

### Build and Run the Application

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. **Set up H2 Database:**
   - Create a database in H2, e.g., `ticket_management_system`.
   - Ensure you have the necessary tables created according to the schema defined in your project.

3. **Configure the Application:**
   - Update the `application.properties` or `application.yml` with your H2 database connection details:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/ticket_management_system
     spring.h2.console.enabled=true
     spring.datasource.url=jdbc:h2:mem:ticketdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=sa
     spring.datasource.password=
      
     # JPA Hibernate Settings
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
     ```

4. **Build the project using Maven:**
   ```bash
   mvn clean install
   ```

5. **Run the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

The application will start running at `http://localhost:8080`.

## Usage Instructions

### Configuring and Starting the System

Once the application is running, you can interact with the CLI by using the available commands. Here's how to use the system:

1. **Start Ticket Release (`start` command):**
   - This will start the process of releasing tickets into the pool.
   - The system will follow the configuration settings such as ticket release rate, total tickets, etc.

2. **Check Ticket Pool Status (`status` command):**
   - This displays the current status of the ticket pool (how many tickets are available).

3. **Stop Ticket Release (`stop` command):**
   - This will stop the release of tickets and halt all customer threads that are attempting to retrieve tickets.

4. **Configure System (`config` command):**
   - You can either load an existing configuration from a file or input configuration manually. Configuration options include:
     - Total tickets
     - Ticket release rate
     - Customer retrieval rate
     - Max ticket capacity

5. **Help (`help` command):**
   - Display a list of available commands.

6. **Exit (`exit` command):**
   - Exit the application gracefully.

### Explanation of UI Controls

The **Ticket Management System** runs entirely through the CLI, so the user interacts with the application through text-based commands. Here’s what each command does:

- **start**: Initiates the ticket release process, starting a vendor thread that simulates the release of tickets at a specified rate.
- **status**: Displays the current number of available tickets in the pool.
- **stop**: Stops the vendor thread and any active customer threads.
- **config**: Allows you to configure the system settings, either manually or by loading a configuration file.
- **help**: Lists all available commands and their functions.
- **exit**: Exits the application.
