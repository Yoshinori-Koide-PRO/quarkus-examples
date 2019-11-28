package org.acme.mongodb.panache;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.mongodb.panache.model.Person;
import org.bson.types.ObjectId;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @POST
    public Person create(Person person) {
        person.persist();
        return person;
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") ObjectId id) {
        return Person.findById(id);
    }
}