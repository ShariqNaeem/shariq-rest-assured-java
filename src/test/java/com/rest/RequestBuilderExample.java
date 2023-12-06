package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestBuilderExample {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void init(){
        //Without Method Chaining
        RequestSpecBuilder requestBuilder= new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://reqres.in");
        requestBuilder.addQueryParam("page=2");
        requestBuilder.log(LogDetail.ALL);

        //With Method Chaining
        RequestSpecBuilder requestBuilder1 = new RequestSpecBuilder();
        requestBuilder1.setBaseUri("https://reqres.in").addQueryParam("page=2").log(LogDetail.ALL);

        //requestSpecification = requestBuilder.build();
        requestSpecification = requestBuilder1.build();
    }

    @Test
    public void statusCodeValidation() {
        Response response = given(requestSpecification).get("/api/users").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }
    @Test
    public void responseBodyValidation() {
        Response response = given(requestSpecification).get("/api/users").then().log().all().extract().response();
        assertThat(response.path("data[0].email"), is(equalTo("george.bluth@reqres.in")));
    }
}
