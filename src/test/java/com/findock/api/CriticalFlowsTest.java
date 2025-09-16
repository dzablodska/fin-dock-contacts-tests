package com.findock.api;

import io.qameta.allure.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

@Tag("api")
@Epic("Contacts API — Pseudocode for missing endpoints")
class CriticalFlowsTest extends TestConfig {

  private static final int KNOWN_CONTACT_ID = 1;
  private static final int NON_EXISTING_ID = 999_999;

  @Feature("Contact Details")
  @Story("Retrieve existing contact by known id")
  @Severity(SeverityLevel.CRITICAL)
  @Test
  @DisplayName("GET /contact/{id} (known) → 200 + data wrapper with correct id")
  void shouldReturnContact_whenIdExists() {
    ApiClient.get("/contact/{id}", KNOWN_CONTACT_ID)
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("data", notNullValue())
            .body("data.id", equalTo(KNOWN_CONTACT_ID));
  }

  @Feature("Contact Details")
  @Story("Return 404 for non-existing contact")
  @Severity(SeverityLevel.CRITICAL)
  @Test
  @DisplayName("GET /contact/{id} (non-existing) → 404")
  void shouldReturn404_whenContactDoesNotExist() {
    ApiClient.get("/contact/{id}", NON_EXISTING_ID)
            .statusCode(404)
            .contentType(containsString("application/json"));
  }

  @Test
  @Disabled("POST /contact not present in the provided API docs")
  @Feature("Create Contact")
  @Story("1")
  @Severity(SeverityLevel.CRITICAL)
  @DisplayName("POST /contact → 201 + org linkage")
  void shouldCreateContact_whenPayloadIsValid() {
    // Build minimal payload {organizationId, firstName, lastName, email/phone}
    // POST /contact -> 201
    // Assert response has id and correct organization linkage
    // Attach request/response to Allure
  }

  @Test
  @Disabled("GET /organization/{id}/contacts?page&size not present in the docs")
  @Feature("List by Organization (true list by orgId with pagination)")
  @Story("3")
  @Severity(SeverityLevel.CRITICAL)
  @DisplayName("GET /organization/{id}/contacts?page=1&size=10 → 200; only that org; stable")
  void shouldListContacts_whenOrganizationIdProvided() {
    // Seed contacts for org A and org B
    // GET /organization/{A}/contacts?page=1&size=10 -> 200
    // Assert every item belongs to org A; repeat call and compare ids list for stability
  }

  @Test
  @Disabled("GET /contacts/search?q=&page&size not present in the docs")
  @Feature("Search Contacts by Name")
  @Story("4")
  @Severity(SeverityLevel.CRITICAL)
  @DisplayName("GET /contacts/search?q=an&page=1&size=10 → 200; stable ranking")
  void shouldReturnHits_whenSearchingByName() {
    // Seed "Anna", "Anson", "Bob"
    // GET /contacts/search?q=an&page=1&size=10 -> 200
    // Assert Anna/Anson present; call twice and assert same order
  }

  @Test
  @Disabled("PATCH /contact/{id} not present in the docs")
  @Feature("Update Contact Details")
  @Story("5")
  @Severity(SeverityLevel.CRITICAL)
  @DisplayName("PATCH /contact/{id} → 200 valid; 400 invalid email")
  void shouldUpdateEmail_whenValid_andReturn400_whenInvalid() {
    // Create -> PATCH email valid -> 200; verify persisted
    // PATCH invalid email -> 400/422 with clear error
  }

  @Test
  @Disabled("DELETE /contact/{id} not present in the docs")
  @Feature("Delete Contact")
  @Story("6")
  @Severity(SeverityLevel.CRITICAL)
  @DisplayName("DELETE /contact/{id} → 204; repeat → 404")
  void shouldDeleteContact_whenExists_andReturn404OnRepeat() {
    // Create -> DELETE -> 204; DELETE again -> 404
  }
}
