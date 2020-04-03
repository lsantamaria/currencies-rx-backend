# Currencies backend

A reactive API that aggregates cryptocurrency data and returns event streams.
## Backend

### Technologies

- Kotlin 1.3
- Spring Boot 2.2
- Spring WebFlux 5
- MongoDB 4.0 (embedded)

### Requirements

- Java JDK 11

### Installation

- Download and install Java JDK 11 from the [Java official webpage](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html). 

- Set JAVA_HOME environment variable to point to this installation.
- Install and run MongoDB

In order to compile/test/run the project, execute the provided gradle wrapper.

### Compilation

```
./gradlew compileKotlin
```
### Test
```
./gradlew test
./gradlew integrationTest
```

### Run
You can run the project either running the JAR or using spring-boot gradle plugin:
```
./gradlew spring-boot:run
```
The API will be deployed at http://localhost:8080.
