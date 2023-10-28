package com.rest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class NonStaticImport {

    @Test
    public void sampleTest(){
        RestAssured.given()
                    .baseUri("https://reqres.in")
                .when()
                    .get("/api/users/2")
                .then()
                    .statusCode(200)
                    .body("data.id", is(equalTo(2)));
    }
}
