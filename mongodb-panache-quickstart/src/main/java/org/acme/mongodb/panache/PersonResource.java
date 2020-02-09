package org.acme.mongodb.panache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.client.MongoClient;

import org.acme.mongodb.panache.model.Person;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.runtime.Repository;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PersonResource {
    @Inject MongoClient mongoClient;
    
    @POST
    public Person create(Person person) {
        Repository<Person> repo = new Repository<>(Person::new);
        repo.collectionSuffix = "alice";
        repo.persist(person);
        
        return person;
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") ObjectId id) {
        Repository<Person> person = new Repository<>(Person::new);
        return person.findById(id);
    }
}