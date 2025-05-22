
# Room Management System (ROMS)

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Environment Variables](#environment-variables)
- [Build and Run](#build-and-run)
- [License](#license)

---

## Overview
The **Room Management System (ROMS)** is a Spring Boot application designed to manage room schedules, lecturers, and related data. It provides APIs for filtering schedules, managing rooms, and handling authentication.

---

## Features
- Manage lecturers, rooms, and schedules.
- Filter schedules by date, room, building, and more.
- Authentication and role-based access control.
- CORS support for cross-origin requests.
- Secure JWT-based authentication.

---

## Technologies Used
- Java 21
- Spring Boot
- PostgreSQL
- Maven
- JWT for Authentication

---

## Prerequisites
- JDK 21 (Ensure `JAVA_HOME` is set up)
- Maven
- Local PostgreSQL server
- `.env.properties` file with required environment variables

---

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Phuc000/Room-Management-Service.git
   cd roms
   ```

2. **Create an `env.properties` file** in the root directory with the following content:
   ```properties
   DB_URL=jdbc:postgresql://localhost:5432/your_database
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_jwt_secret
   JWT_EXPIRATION=86400000
   ```

3. **Install dependencies**
   ```bash
   mvn clean install
   ```

4. **Set up the database**
    - Ensure PostgreSQL is running.
    - Create the database and configure the `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in `env.properties`.

---

## Usage

1. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

2. **Access the application**
    - API: `https://localhost:7288`

---

## Environment Variables

| Variable         | Description                           |
|------------------|---------------------------------------|
| `DB_URL`         | Database connection URL               |
| `DB_USERNAME`    | Database username                     |
| `DB_PASSWORD`    | Database password                     |
| `JWT_SECRET`     | Secret key for JWT                    |
| `JWT_EXPIRATION` | JWT expiration time in milliseconds   |

---

## Build and Run

1. **Build the project**
   ```bash
   mvn clean compile
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

---

## License
__

---
