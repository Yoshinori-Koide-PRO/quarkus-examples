package api.spec;


import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

// @QuarkusTest
@RunWith(ConcordionRunner.class)
public class FruitsFixture {

    public String request(String path) {
        return given()
                .when().get(path)
                .body().asString();
    }
}