package com.findock.api;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ApiClient {

  public static ValidatableResponse get(String path) {
    return given().spec(TestConfig.REQ).when().get(path).then();
  }

  public static ValidatableResponse get(String path, Object... pathParams) {
    return given().spec(TestConfig.REQ).when().get(path, pathParams).then();
  }
}
