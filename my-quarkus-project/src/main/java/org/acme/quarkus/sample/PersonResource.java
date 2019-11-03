package org.acme.quarkus.sample;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.quarkus.sample.model.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @POST
    @Transactional
    public Person create(Person person) {
        person.persist();
        return person;
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Person get(@PathParam("id") Long id) {
        return Person.findById(id);
    }
}