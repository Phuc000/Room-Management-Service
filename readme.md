Dependencies:
- .env requirements in [text](env.properties)
- Local Postgres DB Server
- JDK 21 (with set up JAVA_HOME)
- Maven builder

Build:
- mvn clean compile
- mvn clean spring-boot:run