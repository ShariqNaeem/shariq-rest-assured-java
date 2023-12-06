package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RestRequestBuilderExample {

    @BeforeClass
    public void init(){
        RequestSpecBuilder requestBuilder1 = new RequestSpecBuilder();
        requestBuilder1.setBaseUri("https://reqres.in").addQueryParam("page=2").log(LogDetail.ALL);

        //requestSpecification = requestBuilder.build();
        RestAssured.requestSpecification = requestBuilder1.build();
    }

    @Test
    public void statusCodeValidation() {
        Response response = get("/api/users").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }
    @Test
    public void responseBodyValidation() {
        Response response = get("/api/users").then().log().all().extract().response();
        assertThat(response.path("data[0].email"), is(equalTo("george.bluth@reqres.in")));
    }
}
