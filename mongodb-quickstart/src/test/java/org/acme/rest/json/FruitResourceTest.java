package org.acme.rest.json;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import javax.inject.Inject;

@QuarkusTest
public class FruitResourceTest {

    @Inject
    FruitService fruitService;

    @Disabled
    @Test
    public void testAddFruite() {
        Fruit apple = new Fruit("apple", "this is apple");
        apple.setPath(",apple");
        Assertions.assertEquals(apple, fruitService.add(apple), "not apple");
    }

    @Disabled
    @Test
    public void testAddFruite2() {
        Fruit strawberry = new Fruit("strawberry", "this is strawberry");
        strawberry.setPath(",apple,strawberry");
        Assertions.assertEquals(strawberry, fruitService.add(strawberry), "not strawberry");
    }

    @Test
    public void testFindbyApple() {
        Fruit apple = new Fruit("apple", "this is apple");
        apple.setPath(",apple");

        Assertions.assertEquals(apple, fruitService.findByPath("^,apple").get(0), "not apple");
    }


    @Disabled
    @Test
    public void testHelloEndpoint() {
        given().when().get("/fruits").then().statusCode(200).body(is("hello"));
    }

}
