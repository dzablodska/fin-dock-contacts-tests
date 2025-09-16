package com.findock.api;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Epic("Organizations API")
class OrganizationsApiTest extends TestConfig {

  private static final int KNOWN_ORG_ID = 1;
  private static final int NON_EXISTING_ID = 999_999;

  @Feature("List")
  @Story("Retrieve all organizations")
  @Severity(SeverityLevel.NORMAL)
  @Test
  @DisplayName("GET /organizations → 200 + non-empty data with ids & names")
  void shouldReturnOrganizations_whenOrganizationsEndpointIsCalled() {
    ApiClient.get("/organizations")
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data", not(empty()))
            .body("data.id", hasItem(notNullValue()))
            .body("data.name", hasItem(not(emptyOrNullString())));
  }

  @Feature("Details")
  @Story("Retrieve existing organization by id (id taken from /organizations)")
  @Severity(SeverityLevel.NORMAL)
  @Test
  @DisplayName("GET /organization/{id} (existing) → 200 + correct id and name")
  void shouldReturnOrganization_whenIdExists() {
    ApiClient.get("/organization/{id}", KNOWN_ORG_ID)
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data.id", equalTo(KNOWN_ORG_ID))
            .body("data.name", not(emptyOrNullString()));
  }

  @Feature("Details")
  @Story("Return 404 for non-existing organization")
  @Severity(SeverityLevel.MINOR)
  @Test
  @DisplayName("GET /organization/{id} (non-existing) → 404")
  void shouldReturn404_whenOrganizationDoesNotExist() {
    ApiClient.get("/organization/{id}", NON_EXISTING_ID)
            .statusCode(404)
            .contentType(containsString("application/json"));
  }
}
