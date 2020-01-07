package org.acme.mongodb.panache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/fruits")
public class FruitResoruce {

    @GET
    public String get() {
        return "hello";
    }
}