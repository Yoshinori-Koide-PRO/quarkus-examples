package org.acme.mongodb.panache;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import java.time.LocalDate;
import org.acme.mongodb.panache.model.Person;
import org.acme.mongodb.panache.model.Person.Status;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testHelloEndpoint() {

        given().contentType(ContentType.JSON)
                .body("{\"name\": \"Alice\", \"birthDate\":\"1980-12-31\",\"status\":\"Alive\"}")
                .when().post("/person").then().statusCode(200).body(containsString("Alice"));
    }

    @Disabled
    @Test
    public void testPostPersonEndpoint() {
        given().when().get("/person").then().statusCode(200).body(is("hello"));
    }

}
