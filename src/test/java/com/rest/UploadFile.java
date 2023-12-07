package com.rest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UploadFile {

    @Test
    public void fileUploadTest(){
        String attributes = "{\"name\": \"morpheus\",\"parent\":{\"id\":\"123456\"}}";
        RestAssured.given()
                    .baseUri("https://postman-echo.com")
                    .multiPart("file",new File("src/main/resources/temp.txt"))
                    .multiPart("attributes", attributes, "application/json")
                    .when()
                        .post("/post")
                    .then()
                        .log().all()
                    .assertThat()
                    .statusCode(200);
    }
}
