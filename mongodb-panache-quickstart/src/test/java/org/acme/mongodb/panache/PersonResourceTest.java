package org.acme.mongodb.panache;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonResourceTest {

    @Test
    public void testPersonEndpoint_Alice() {
        String alice = "{\"name\":\"Alice\",\"birth\":\"2010-16-11\",\"status\":\"Alive\"}";

        given().
            contentType("application/json").
        when().
            body(alice).
            post("/person").
        then().
            log().all().
            statusCode(200).
            body("name", is("Alice"));
    }

    @Test
    public void testPersonEndpointFindByName() {
        given().
            queryParam("name", "Alice").
        when().
            get("/person").
        then().
            log().all().
            statusCode(200).
            body("name", is("Alice"));
    }

    @Test
    public void testPersonEndpoint_FriendWithAlice() {
        String alice = "{\"name\":\"Alice\",\"birth\":\"2010-16-11\",\"status\":\"Alive\"}";

        given().
            contentType("application/json").
        when().
            body(alice).
            post("/person/friendWith/john").
        then().
            log().all().
            statusCode(200).
            body("name", is("john"));
    }
}