package com.rest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidator {

    @Test
    public void jsonSchemaValidatorTest(){
        given()
                    .baseUri("https://postman-echo.com")
                .when()
                    .get("/get")
                .then()
                    .log().all()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemaValidator.json"));
    }
}
