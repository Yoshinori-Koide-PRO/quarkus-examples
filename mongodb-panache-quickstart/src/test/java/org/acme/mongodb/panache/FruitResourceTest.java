package org.acme.mongodb.panache;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@Disabled
@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().
        when().
            get("/fruits").
        then().
            statusCode(200).
            body(is("hello"));
    }

}