package com.findock.api;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Epic("Users & Accounts API")
class UsersAndAccountsApiTest extends TestConfig {

  private static final int KNOWN_USER_ID = 1;
  private static final int KNOWN_ACCOUNT_ID = 1;
  private static final int NON_EXISTING_ID = 999_999;

  @Feature("Users")
  @Story("List users")
  @Severity(SeverityLevel.NORMAL)
  @Tag("smoke")
  @Test
  @DisplayName("GET /users → 200 + non-empty data with at least one id")
  void shouldReturnUsers_whenUsersEndpointIsCalled() {
    ApiClient.get("/users")
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data", not(empty()))
            .body("data.id", hasItem(notNullValue()));
  }

  @Feature("Users")
  @Story("Get user by id (existing from /users)")
  @Severity(SeverityLevel.NORMAL)
  @Test
  @DisplayName("GET /user/{id} (existing) → 200 + correct id")
  void shouldReturnUser_whenIdExists() {
    ApiClient.get("/user/{id}", KNOWN_USER_ID)
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data.id", equalTo(KNOWN_USER_ID));
  }

  @Feature("Users")
  @Story("Get user by id (non-existing)")
  @Severity(SeverityLevel.NORMAL)
  @Test
  @DisplayName("GET /user/{id} (non-existing) → 404")
  void shouldReturn404_whenUserDoesNotExist() {
    ApiClient.get("/user/{id}", NON_EXISTING_ID)
            .statusCode(404)
            .contentType(containsString("application/json"));
  }

  @Feature("Accounts")
  @Story("Get account by id (existing)")
  @Severity(SeverityLevel.TRIVIAL)
  @Test
  @DisplayName("GET /account/{id} (existing) → 200 + correct id")
  void shouldReturnAccount_whenIdExists() {
    ApiClient.get("/account/{id}", KNOWN_ACCOUNT_ID)
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data.id", equalTo(KNOWN_ACCOUNT_ID));
  }

  @Feature("Accounts")
  @Story("Get account by id (non-existing)")
  @Severity(SeverityLevel.TRIVIAL)
  @Test
  @DisplayName("GET /account/{id} (non-existing) → 404")
  void shouldReturn404_whenAccountDoesNotExist() {
    ApiClient.get("/account/{id}", NON_EXISTING_ID)
            .statusCode(404)
            .contentType(containsString("application/json"));
  }
}
