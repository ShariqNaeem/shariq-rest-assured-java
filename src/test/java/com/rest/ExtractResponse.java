package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ExtractResponse {
    @Test
    public void extractResponseBody() {
        Response res = given().
                baseUri("https://reqres.in").
                //header("",""). We can define the Header/Headers
                        queryParam("page=2"). // we can define it with the endpoint(method)
                        when().
                get("/api/users").
                then().
                statusCode(200).
                extract().
                response();

        System.out.println(res.asString()); //Print the entire response here and used asString() to convert it into String

    }

    @Test
    public void extract_Single_Value_From_Response1() {
        Response res =
                given().
                        baseUri("https://reqres.in").
                        queryParam("page=2").
                when().
                        get("/api/users").
                then().
                        statusCode(200).
                        extract().
                        response();

        System.out.println("EMAIL: " + res.path("data[0].email")); // .path() takes the json and xml path

        JsonPath jsonPath = new JsonPath(res.asString());
        System.out.println(jsonPath.getInt("data[0].id")); // Used .getInt() because the data type of id

    }

    @Test
    public void extract_Single_Value_From_Response2() {
        String res =
                given().
                        baseUri("https://reqres.in").
                        queryParam("page=2").
                when().
                        get("/api/users").
                then().
                        statusCode(200).
                        extract().
                        response().
                        asString();

        System.out.println("TEXT: " + JsonPath.from(res).getString("support.text")); // Used .getString() because the data type of support.text
    }

    @Test
    public void extract_Single_Value_From_Response3() {
        String email =
                given().
                        baseUri("https://reqres.in").
                        queryParam("page=2").
                when().
                        get("/api/users").
                then().
                        statusCode(200).
                        extract().
                        response().
                        path("data[5].email"); // Used directly .path() that will return email. If I want to fetch the id then it should assign to the integer variable

        System.out.println(email);
        assertThat(email, equalTo("tracey.ramos@reqres.in")); // Hamcrest matcher assertion
        Assert.assertEquals(email, "tracey.ramos@reqres.in"); // TestNG assertion

    }
}
