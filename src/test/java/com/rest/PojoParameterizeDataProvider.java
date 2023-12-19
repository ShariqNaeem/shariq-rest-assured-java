package com.rest;

import com.rest.pojo.User;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

public class PojoParameterizeDataProvider {
    @BeforeClass
    public void init() {

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://reqres.in")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(201).log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test(dataProvider = "USERS")
    public void validatePostRequestWithPojoDataProvider(String name, String job) {
        User user = new User(name, job);

        User responseUser = given()
                .body(user)
        .when()
                .post("/api/users")
        .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .as(User.class);

        assertThat(responseUser.getId(), notNullValue());
        assertThat(responseUser.getCreatedAt(), matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?Z$"));
    }

    @DataProvider(name = "USERS")
    public Object[][] getUsers(){
        return new Object[][]{
            {"Shariq","Automation Engineer"},
            {"Testing","Teacher"}
        };
    }

}
