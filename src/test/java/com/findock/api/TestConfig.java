package com.findock.api;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.config.RestAssuredConfig;

import static io.restassured.config.HttpClientConfig.httpClientConfig;
import org.junit.jupiter.api.BeforeAll;

/**
 * Centralized test configuration:
 * - Safe defaults for public demo URLs (API_BASE, APP_BASE)
 * - No defaults for credentials (APP_EMAIL, APP_PASS) → must come from .env/env vars/-D
 * - Shared RestAssured setup with logging and timeouts
 */
public abstract class TestConfig {
  private static Dotenv dotenv;

  public static final String API_BASE;
  public static final String APP_BASE;
  public static final String APP_EMAIL;
  public static final String APP_PASS;

  protected static RequestSpecification REQ;

  static {
    dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    API_BASE = System.getProperty("api.base",
            dotenv.get("API_BASE", System.getenv().getOrDefault("API_BASE",
                    "https://qa-panel-assignment-214de3946f4e.herokuapp.com/api")));

    APP_BASE = System.getProperty("app.base",
            dotenv.get("APP_BASE", System.getenv().getOrDefault("APP_BASE",
                    "https://qa-panel-assignment-214de3946f4e.herokuapp.com/")));

    APP_EMAIL = System.getProperty("app.email",
            dotenv.get("APP_EMAIL", System.getenv("APP_EMAIL")));
    APP_PASS  = System.getProperty("app.pass",
            dotenv.get("APP_PASS", System.getenv("APP_PASS")));
  }

  @BeforeAll
  static void setupRestAssured() {
    RestAssured.baseURI = API_BASE;
    RestAssured.config = RestAssuredConfig.config()
            .httpClient(httpClientConfig()
                    .setParam("http.connection.timeout", 10000)
                    .setParam("http.socket.timeout", 10000)
                    .setParam("http.connection-manager.timeout", 10000L));

    REQ = new RequestSpecBuilder()
            .setAccept("application/json")
            .log(LogDetail.URI)
            .build()
            .filter(new RequestLoggingFilter(LogDetail.METHOD))
            .filter(new ResponseLoggingFilter(LogDetail.STATUS));
  }
}
