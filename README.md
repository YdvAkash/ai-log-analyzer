# 🛡️ Sentinel-AI: Intelligent Microservices Observability Pipeline

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.6.1-blue.svg)
![MongoDB](https://img.shields.io/badge/MongoDB%20Atlas-7.0-green.svg)
![AWS Bedrock](https://img.shields.io/badge/AI-AWS%20Bedrock-blueviolet.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

Sentinel-AI is a high-performance, distributed observability pipeline designed to ingest, parse, and analyze system logs using **Generative AI (AWS Bedrock)**. It automates the detection of critical system failures and provides developers with immediate **Root Cause Analysis (RCA)** and **Fix Suggestions**.

---

## 🏗️ System Architecture

The project follows a decoupled, event-driven microservices architecture using **Apache Kafka** as the backbone.



### 🔄 The Data Pipeline
1.  **Ingestion Service (8089)**: Receives raw logs via REST and pushes to `raw-logs` topic.
2.  **Parsing Service (8082)**: Cleans data, extracts LogLevels using Regex, persists to **MongoDB Atlas**, and triggers `clustered-logs`.
3.  **AI Analysis Service (8084)**: The "Brain." Uses **AWS Bedrock (Claude 3)** to analyze stack traces and identify unique error patterns (Clustering).
4.  **Alert Service**: Dedicated service that consumes analysis results and fires real-time **Email notifications**.

---

## ✨ Features

* **Asynchronous Processing**: Uses Kafka to ensure the source application never slows down during high log volumes.
* **AI Root Cause Analysis**: Integrated with **AWS Bedrock** to provide human-readable solutions for complex errors like *Circular Dependencies* or *DB Connection Leaks*.
* **Intelligent Clustering**: Fingerprints unique errors to avoid redundant AI calls, reducing latency and AWS costs.
* **Fault Tolerant**: Implements `ErrorHandlingDeserializer` to handle Kafka "Poison Pills" without crashing the pipeline.
* **Polyglot Persistence**: 
    * **MongoDB Atlas**: For persistent audit trails.
    * **Elasticsearch**: Optimized for high-speed log searching.

---

## 🛠️ Technology Stack

| Category | Technology |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 3.2.2 |
| **Messaging** | Apache Kafka 3.6.1 |
| **AI Integration** | AWS Bedrock (Anthropic Claude 3 / Llama 3) |
| **Databases** | MongoDB Atlas, Elasticsearch (Bypass supported) |
| **Infrastructure** | Docker, Zookeeper-less Kafka |

---

## 🚀 Installation & Setup

### 1. Prerequisites
* **Java 21** installed.
* **Kafka** running on `localhost:9092`.
* **AWS Credentials** with Bedrock access.

### 2. Configuration (`application.yml`)
Update the AI Analysis service with your credentials:
```yaml
aws:
  accessKey: "YOUR_ACCESS_KEY"
  secretKey: "YOUR_SECRET_KEY"
  region: "ap-south-1"

spring:
  data:
    mongodb:
      uri: "your-mongodb-atlas-uri"
3. Build & Run
Bash
# Install common DTOs first
cd common-library && mvn clean install

# Build and run all services
mvn clean install -DskipTests
mvn spring-boot:run
🧪 Advanced Testing (Postman)
Simulate a complex production failure (e.g., Circular Dependency):

Endpoint: POST http://localhost:8080/api/v1/logs
Body:

JSON
{
  "serviceName": "payment-gateway-service",
  "logLevel": "FATAL",
  "environment": "PRODUCTION",
  "rawMessage": "org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'paymentProcessor': Is there an unresolvable circular reference?",
  "metadata": {
    "clusterId": "PAY-GATEWAY-CLUSTER-001",
    "impact": "CRITICAL - 100% Payment Failures"
  }
}
💡 Key Interviewer Talking Points
Event-Driven Design: Why Kafka? To handle backpressure and ensure loose coupling between Ingestion and AI layers.

Poison Pill Handling: How I prevented consumer crashes using ErrorHandlingDeserializer and Manual Type Mapping.

Cost Optimization: Implementing a clustering mechanism to ensure LLM calls are only made for unique error patterns.

MTTR Reduction: The system shifts observability from "What happened?" to "How do I fix it?", saving hours of manual debugging.

👨‍💻 Author
Akash Yadav SDE | B.Tech ECE Student LinkedIn | GitHub | YdvAkash |