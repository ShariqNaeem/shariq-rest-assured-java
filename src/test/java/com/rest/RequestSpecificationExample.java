package com.rest;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

public class RequestSpecificationExample {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void init(){
        requestSpecification = with() // we can use given() method as well
                .baseUri("https://reqres.in")
                .queryParam("page=2");
    }
    @Test
    public void ExampleRequestSpecification() {
        //given() return the request specification So we can send the request specification in the parameter..
//        RequestSpecification requestSpecification = given()
//                .baseUri("https://reqres.in")
//                .queryParam("page=2");

        //given(requestSpecification) Both will work
        given().spec(requestSpecification)
        .when()
                .get("/api/users")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                // "$" define the root-object of the response body
                .body("$", hasKey("page"), // Validate the single-key

                        // Validate the multiple keys
                        "$", allOf(
                                hasKey("per_page"),
                                hasKey("total_pages"),
                                hasKey("data")
                        ),

                        "data[3]", hasEntry("id", 4),

                        "data", hasSize(6) // Another way to validate the size/length of array
                );
    }

    @Test
    public void ExampleRequestSpecification2() {
        //given() return the request specification So we can send the request specification in the parameter..
//        RequestSpecification requestSpecification = given()
//                .baseUri("https://reqres.in")
//                .queryParam("page=2");

        //given(requestSpecification) Both will work
        given().spec(requestSpecification)
                .when()
                .get("/api/users")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                // "$" define the root-object of the response body
                .body("$", hasKey("page"), // Validate the single-key

                        // Validate the multiple keys
                        "$", allOf(
                                hasKey("per_page"),
                                hasKey("total_pages"),
                                hasKey("data")
                        ),
                        "data[3]", hasEntry("id", 4)
                );
    }
}
