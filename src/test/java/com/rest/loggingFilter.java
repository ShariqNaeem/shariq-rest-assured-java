package com.rest;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

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

    @Test
    public void loggingToFileTest() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File("logToFile.log"));
        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(printStream))
                .filter(new ResponseLoggingFilter(printStream))
                .when()
                .get("/get")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemaValidator.json"));
    }
}
