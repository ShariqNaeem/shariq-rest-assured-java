package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePOST {
    @BeforeClass
    public void init(){

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://reqres.in").log(LogDetail.ALL);
        RestAssured.requestSpecification = requestBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(201).log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validatePostRequestInBDD() {
        String requestPayload = "{\n" +
                "    \"name\": \"shariq\",\n" +
                "    \"job\": \"naeem\"\n" +
                "}\n";
        given()
                .body(requestPayload)
        .when()
                .post("/api/users")
        .then()
                .assertThat()
                .body("id", notNullValue())
                .body("createdAt",matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?Z$"));
    }
}
