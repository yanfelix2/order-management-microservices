# Order Management Microservices

This project is a microservices-based architecture developed with **Java** and **Spring Boot**, focusing on asynchronous order processing using **RabbitMQ** for messaging and **MySQL** for data persistence.

## 🚀 Project Architecture

The system is divided into two main services:

1. **ms-pedido (Order Service)**: Responsible for receiving orders, persisting data in MySQL (handling the One-to-Many relationship with order items), and publishing the order ID to the RabbitMQ queue.
2. **ms-processamento (Processing Service)**: Consumes messages from the queue and processes the orders based on business rules (e.g., updating status based on order ID).

## 🛠️ Technologies Used

* **Java 17/21**
* **Spring Boot 3+**
* **Spring Data JPA**
* **MySQL** (Persistence)
* **RabbitMQ** (Messaging via CloudAMQP)
* **Jackson** (JSON Mapping)

## 🏗️ Repository Structure

* `/ms-pedido`: Source code for the order entry microservice.
* `/ms-processamento`: Source code for the order processing microservice.
* `.gitignore`: Global configuration to ignore IDE-specific and sensitive files.

## 📈 Planned Features (Roadmap)

As tracked in our project [Issues](https://github.com/yanfelix2/order-management-microservices/issues/1), the next steps include:
* Implementing **Docker** and **Docker Compose** for orchestration.
* Environment configuration for **AWS** deployment.
* Exploring **Kafka** for high-throughput event streaming.

---
*This project is part of my studies focused on distributed systems and SOLID principles.*
