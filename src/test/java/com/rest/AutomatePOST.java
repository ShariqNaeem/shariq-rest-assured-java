package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePOST {
    @BeforeClass
    public void init() {

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
                .body("createdAt", matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?Z$"));
    }

    @Test
    public void validatePostRequestInNNonBDD() {
        File requestPayload = new File("src/main/resources/createUserPayload.json");

        //We can use HASHMAP as well. If we have nested object than the syntax will be:
        HashMap<String, Object> mainObj= new HashMap<String, Object>();

        // Creating the nested object here
        HashMap<String, String> nestedObj= new HashMap<String, String>();
        nestedObj.put("key1", "value1");
        nestedObj.put("key2", "value2");
        nestedObj.put("key3", "value3");

        mainObj.put("workspace", nestedObj);

        Response response = with()
                .body(requestPayload)
                .post("/api/users");

        assertThat(response.path("id"), notNullValue());
        assertThat(response.path("createdAt"), matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?Z$"));
    }
}
