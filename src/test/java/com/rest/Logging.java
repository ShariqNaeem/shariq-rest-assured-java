package com.rest;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Logging {

    @Test
    public void log_Request_And_Response_Test() {
        given()
                .baseUri("https://reqres.in")
                .log().all()
        .when()
                .get("/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(equalTo(2)));
    }

    @Test
    public void log_ReqHeaders_And_ResBody_Test() {
        given()
                .baseUri("https://reqres.in")
                .log().headers()
        .when()
                .get("/api/users/2")
        .then()
                .log().body()
                .statusCode(200)
                .body("data.id", is(equalTo(2)));
    }

    @Test
    public void log_Only_If_Error() {
        given()
                .baseUri("https://reqres.in")
        .when()
                .get("/api/users/2000")
        .then()
                .log().ifError()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void log_Only_If_Validation_Fails() {
        given()
                .baseUri("https://reqres.in")
                .log().ifValidationFails()
        .when()
                .get("/api/users/2000")
        .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void log_Only_If_Validation_Fails_By_Config() {
        given()
                .baseUri("https://reqres.in")
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
        .when()
                .get("/api/users/2000")
        .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void log_BlackList_Headers() {
        Set<String> headers = new HashSet<String>();
        headers.add("Accept"); // Accept = */*

        given()
                .baseUri("https://reqres.in")
                //.config(config.logConfig(LogConfig.logConfig().blacklistHeader("Accept")))
                .config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))) // Accept=[ BLACKLISTED ]
                .log().all()
        .when()
                .get("/api/users/2")
        .then()
                .assertThat()
                .statusCode(200);
    }
}
