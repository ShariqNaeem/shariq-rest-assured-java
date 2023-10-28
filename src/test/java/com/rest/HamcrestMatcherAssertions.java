package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HamcrestMatcherAssertions {
    @Test
    public void HamcrestMatcherAssertionsCollection() {
        given()
                .baseUri("https://reqres.in")
                .queryParam("page=2")
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

                        // It's groovy gson syntax
                        "data.id", hasItems(1, 2, 3, 4, 5), // Validate the id's present in the data(array of object)
                        "data.first_name", hasItems("George", "Janet", "Emma", "Eve", "Charles", "Tracey"),
                        "data.last_name", hasItem("Holt"),
                        "data[3].email", is(equalTo("eve.holt@reqres.in")),
                        "data.size()", equalTo(6), // Validate the length of array

                        "support.url", containsString("support-heading"), // Validate that url-value containing the text

                        "data.first_name", containsInAnyOrder("Eve", "Charles", "Tracey", "George", "Janet", "Emma"), // Following any order

                        "data.first_name", contains("George", "Janet", "Emma", "Eve", "Charles", "Tracey"), // Following the strict order

                        "data[2].last_name", is(not(empty())),

                        "data[3]", hasEntry("id", 4),

                        "data", hasSize(6) // Another way to validate the size/length of array
                );


    }
}
