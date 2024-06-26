package com.modasby;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ExampleResourceTest {

    @Inject
    JWTUtil jwtUtil;

    @Test
    void testInvalidToken() {
        given()
          .when().get("/foo-bar")
          .then()
             .statusCode(401);
    }

    @Test
    void testValidToken() {
        String token = given()
                        .contentType("application/json")
                        .when()
                        .body(new CredentialsDto("giulliano", "123456"))
                        .post("/signup")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        assertTrue(jwtUtil.authenticateToken(token));
    }

    @Test
    void testAuthentication() {
        String token = jwtUtil.generateToken("giulliano");

        given()
            .when()
            .body(new CredentialsDto("giulliano", "123456"))
            .header(new Header("Authorization", token))
            .get("/foo-bar")
            .then()
            .statusCode(204);
    }
}