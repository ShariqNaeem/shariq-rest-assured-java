package com.rest;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class loggingFilter {

    @Test
    public void loggingFilterTest(){
        given()
                    .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.HEADERS))
                .filter(new ResponseLoggingFilter(LogDetail.BODY))
                .when()
                    .get("/get")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemaValidator.json"));
    }
}
