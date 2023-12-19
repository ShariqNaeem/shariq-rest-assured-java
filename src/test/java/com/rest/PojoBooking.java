package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.Booking;
import com.rest.pojo.BookingDates;
import com.rest.pojo.BookingResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PojoBooking {
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
    public void validateBookingPostRequestWithPojo() throws JsonProcessingException {
        BookingDates bookingDates = new BookingDates("2024-01-01","2024-02-02");
        Booking booking = new Booking("Shariq", "Naeem", 1500, true, bookingDates);

        BookingResponse response = given()
                .body(booking)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .post("/booking")
        .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .as(BookingResponse.class);

        assertThat(response.getBookingid(), notNullValue());

        ObjectMapper objectMapper = new ObjectMapper();
        String deserializePojoResponse = objectMapper.writeValueAsString(response.getBooking());
        String deserializePojoRequest = objectMapper.writeValueAsString(booking);

        assertThat(objectMapper.readTree(deserializePojoResponse), equalTo(objectMapper.readTree(deserializePojoRequest)));
    }
}
