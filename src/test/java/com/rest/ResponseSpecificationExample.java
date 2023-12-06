package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ResponseSpecificationExample {
    ResponseSpecification responseSpecification ;

    @BeforeClass
    public void init(){

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://reqres.in").addQueryParam("page=2").log(LogDetail.ALL);

        RestAssured.requestSpecification = requestBuilder.build();

        responseSpecification = RestAssured.expect().statusCode(200);
    }

    @Test
    public void statusCodeValidation() {
        get("/api/users").then().spec(responseSpecification).log().all();
    }
    @Test
    public void responseBodyValidation() {
        Response response = get("/api/users").then().spec(responseSpecification).log().all().extract().response();
        assertThat(response.path("data[0].email"), is(equalTo("george.bluth@reqres.in")));
    }
}
