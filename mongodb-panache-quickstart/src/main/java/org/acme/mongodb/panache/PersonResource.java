package org.acme.mongodb.panache;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.acme.mongodb.panache.model.Friend;
import org.acme.mongodb.panache.model.Person;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.runtime.Repository;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonResource {
    
    @GET
    public Person findByName(@QueryParam("name") String name) {
        Repository<Person> person = new Repository<>(Person::new);
        person.collectionSuffix = name;
        return person.find("name", name).firstResult();
    }
    @POST
    public Person create(Person person) {
        Repository<Person> repo = new Repository<>(Person::new);
        repo.collectionSuffix = person.name;
        repo.persist(person);
        
        return person;
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") ObjectId id) {
        Repository<Person> person = new Repository<>(Person::new);
        return person.findById(id);
    }

    @POST
    @Path("friendWith/{name}")
    public Friend friendWith(Person person, @PathParam("name") String friendName) {
        Repository<Person> repo = new Repository<>(Person::new);
        repo.collectionSuffix = person.name;
        Person p = repo.find("name", person.name).firstResult();

        Repository<Friend> repoFriend = new Repository<>(Friend::new);
        repoFriend.collectionSuffix = person.name;
        Friend f = new Friend();
        f.name = friendName;
        f.friendWith = p;

        repoFriend.persist(f);
        return f;
    }
}