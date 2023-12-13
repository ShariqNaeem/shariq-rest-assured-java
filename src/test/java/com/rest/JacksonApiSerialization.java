package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

public class JacksonApiSerialization {
    @BeforeClass
    public void init() {

        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://restful-booker.herokuapp.com")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void createBooking() {
//        {
//            "firstname" : "Jim",
//                "lastname" : "Brown",
//                "totalprice" : 111,
//                "depositpaid" : true,
//                "bookingdates" : {
//                     "checkin" : "2018-01-01",
//                    "checkout" : "2019-01-01"
//        }

        HashMap<String, String> bookingDatesObj = new HashMap<String,String>();
        bookingDatesObj.put("checkin","2018-01-01");
        bookingDatesObj.put("checkout","2019-09-09");

        HashMap<String, Object> mainObject = new HashMap<String,Object>();
        mainObject.put("bookingdates",bookingDatesObj);
        mainObject.put("depositpaid",true);
        mainObject.put("totalprice",111);
        mainObject.put("lastname","NAEEM");
        mainObject.put("firstname","SHARIQ");

        given()
                .body(mainObject)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .post("/booking")
        .then()
                .assertThat()
                .body("bookingid", notNullValue());
    }

    // We can use the ObjectNode as well
    @Test
    public void serializeMapToJsonUsingJackson() throws JsonProcessingException {
        HashMap<String, String> bookingDatesObj = new HashMap<String,String>();
        bookingDatesObj.put("checkin","2018-01-01");
        bookingDatesObj.put("checkout","2019-09-09");

        HashMap<String, Object> mainObject = new HashMap<String,Object>();
        mainObject.put("bookingdates",bookingDatesObj);
        mainObject.put("depositpaid",true);
        mainObject.put("totalprice",111);
        mainObject.put("lastname","NAEEM");
        mainObject.put("firstname","SHARIQ");

        //ObjectMapper is from com.fasterxml.jackson.databind.ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String bodyData = objectMapper.writeValueAsString(mainObject);

        given()
                .body(bodyData)
                .header("Content-Type", "application/json; charset=utf-8")
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .body("bookingid", notNullValue());
    }
    @Test
    public void serializeListToJsonUsingJackson() throws JsonProcessingException {
        HashMap<String,String> obj5001 = new HashMap<String,String>();
        obj5001.put ("id","5001");
        obj5001.put ("type","None");

        HashMap<String,String> obj5002 = new HashMap<String,String>();
        obj5002.put("id","5002");
        obj5002.put("type","Glazed");

        List<HashMap<String, String>> jsonlist = new ArrayList<>();
        jsonlist.add(obj5001) ;
        jsonlist.add(obj5002);

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyData = objectMapper.writeValueAsString(jsonlist);

        given().
                body(jsonlist)
        .when()
                .post("/post")
        .then()
                .assertThat()
                .body("bookingid", notNullValue());
    }
}
