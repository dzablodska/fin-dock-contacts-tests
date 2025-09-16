# FinDock Panel — API Tests

This project contains automated API tests for the **FinDock QA Panel Assignment**.  
It demonstrates best practices in **Java test automation**, testing of critical flows and pseudocode for future endpoints, including configuration via environment variables, `.env` support, Allure reporting, and CI integration.

---

## Tech Stack
- **Java 17**
- **JUnit 5** (test runner & tagging)
- **RestAssured** (API testing)
- **Allure** (test reporting)
- **dotenv-java** (local `.env` support)
- **Maven Surefire** (test execution)
- **GitHub Actions** (CI pipeline)

---

## Project Structure
```
fin-dock-panel-api/
  ├── pom.xml
  ├── .env.example
  ├── src/test/java/com/findock/api/
  │     ├── ApiClient.java
  │     ├── TestConfig.java
  │     ├── CriticalFlows.java
  │     ├── OrganizationsApiTest.java
  │     ├── UsersAndAccountsApiTest.java
  ├── src/test/resources/schemas/
  │     └── organizations.json
  └── target/allure-results/ (generated after tests)
```

---

## Configuration

### Environment Variables
Tests read config in this priority:
1. **System property** (`-Dapi.base=…`)
2. **.env file** (loaded automatically)
3. **Environment variable**

## Running Tests

### Run all tests
```bash
mvn test
```

### Run only API tests
```bash
mvn test -Dgroups=api
```

---

## Allure Reports

### Generate and serve report
```bash
allure serve target/allure-results
```
This opens a local server in your browser.

### Generate static HTML report
```bash
allure generate target/allure-results -o allure-report --clean
```
Open `allure-report/index.html` in your browser.

---

## Test Naming Convention
All tests follow the **`should...when...`** style for clarity, e.g.:
```java
void shouldReturnOrganizations_whenOrganizationsEndpointIsCalled()
```
This makes reports self-describing and readable for non-technical reviewers.

---

## Best Practices Demonstrated
- Separation of **test config** (`TestConfig`) and **test logic**
- Configurable base URL, user, and password (no hardcoded secrets)
- `.env` support for local runs (`dotenv-java`)
- **Tag** (`api`) to run subsets of tests
- JSON schema placeholder (`organizations.json`) to illustrate contract validation
- Logging of requests & responses for debuggability
- CI ready (GitHub Actions workflow included)

---

## Extending the Suite
- Add JSON schemas for `/users`, `/contacts`, etc. and validate with RestAssured’s `json-schema-validator`
- Add **negative tests** for invalid payloads
- Capture and attach raw request/response to Allure
- Add new feature tests (e.g., **Timeline** events) once API is available

---

## CI Integration
A GitHub Actions workflow (`.github/workflows/tests.yml`) is included:
- Runs on push/PR
- Java 17
- Executes tagged tests
- Uploads Allure results as an artifact
