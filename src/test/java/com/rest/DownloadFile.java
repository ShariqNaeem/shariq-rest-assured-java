package com.rest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {

    @Test
    public void fileUploadTestUsingBytes() throws IOException {
        byte[] bytes = given()
                        .baseUri("https://raw.githubusercontent.com")
                        .log().all()
                    .when()
                        .get("/appium/appium/master/packages/appium/sample-code/apps/ApiDemos-debug.apk")
                    .then()
                        .log().all()
                        .extract()
                        .response().asByteArray();


        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
        os.write(bytes);
        os.close();
    }

    @Test
    public void fileUploadTestUsingInputStream() throws IOException {
        InputStream is = given()
                    .baseUri("https://raw.githubusercontent.com")
                    .log().all()
                .when()
                    .get("/appium/appium/master/packages/appium/sample-code/apps/ApiDemos-debug.apk")
                .then()
                    .log().all()
                    .extract()
                    .response().asInputStream();


        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        os.write(bytes);
        os.close();
    }
}
