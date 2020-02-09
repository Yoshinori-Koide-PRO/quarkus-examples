package api.spec;


import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;

@Ignore
@RunWith(ConcordionRunner.class)
public class HelloAPIFixture {

    public String request(String path) {
        return given()
                .when().get(path)
                .body().asString();
    }
}