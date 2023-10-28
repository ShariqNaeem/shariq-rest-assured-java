package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class AutomateGET {
    @Test
    public void validate_Get_StatusCode_And_ResponseBody() {
        given()
                .baseUri("https://reqres.in")
                //.header("","") We can define the Header/Headers
                .queryParam("page=2") // we can define it with the endpoint(method)
        .when()
                .get("/api/users")
        .then()
                .log().all() // for logging all the response details
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

                        "support.url", containsString("support-heading")); // Validate that url-value containing the text
    }
}
