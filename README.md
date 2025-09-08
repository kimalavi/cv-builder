
# 🧰 CV Builder

A simple Java-based application for generating professional CVs. Built with Maven, this project aims to streamline the process of creating structured, customizable resumes using a clean and extensible architecture.

## 🚀 Features

- Create CVs with customizable sections (Education, Experience, Skills, etc.)
- Export CVs in structured formats (e.g., plain text, PDF — coming soon!)
- Easy-to-extend design for adding new templates or fields
- CLI-based interface (GUI planned for future versions)

## 📦 Tech Stack

- Java 21+
- Maven for dependency management
- Modular architecture for scalability

## 🛠️ Getting Started

### Prerequisites

- Java 21 or higher
- Maven installed

### Installation

```bash
git clone https://github.com/kimalavi/cv-builder.git
cd cv-builder
mvn clean install
```

### Running the App

```bash
mvn exec:java -Dexec.mainClass="com.kimalavi.cvbuilder.Main"
```

## 📁 Project Structure

```
cv-builder/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── kimalavi/
│                   └── cvbuilder/
│                       ├── model/
│                       ├── service/
│                       └── Main.java
├── pom.xml
└── README.md
```

## 🧪 Testing

To run unit tests:

```bash
mvn test
```
