package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class StaticImport {

    @Test
    public void sampleTest(){
        given()
                .baseUri("https://reqres.in")
        .when()
                .get("/api/users/2")
        .then()
                .statusCode(200)
                .body("data.id", is(equalTo(2)));
    }
}
