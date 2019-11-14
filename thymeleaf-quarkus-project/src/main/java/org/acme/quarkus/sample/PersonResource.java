package org.acme.quarkus.sample;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.quarkus.sample.model.Person;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.plugins.providers.html.Renderable;

/**
 * PersonResource
 */
@Path("/person")
public class PersonResource {

    ThymeleafRenderer views;

    @Inject
    public PersonResource(ThymeleafRenderer views) {
        this.views = views;
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "performed_create", description = "How many it have been called.")
    @Timed(name = "checksTimer_create", description = "A measure of how long it takes to perform creating person.", unit = MetricUnits.MILLISECONDS)
    public Person create(Person person) {
        person.persist();
        return person;
    }

    @GET
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.TEXT_HTML)
    @Counted(name = "performed_html", description = "How many it have been called.")
    @Timed(name = "checksTimer_html", description = "A measure of how long it takes to perform getting person.", unit = MetricUnits.MILLISECONDS)
    public Renderable getHTML(@PathParam("id") Long id) {
        return views
            .view("person.html")
            .with("p", Person.findById(id));
    }    
}