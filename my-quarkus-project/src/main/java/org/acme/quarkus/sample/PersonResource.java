package org.acme.quarkus.sample;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.metrics.annotation.Counted;

import org.acme.quarkus.sample.model.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

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
    @Counted(name = "performed_get", description = "How many it have been called.")
    @Timed(name = "checksTimer_get", description = "A measure of how long it takes to perform getting person.", unit = MetricUnits.MILLISECONDS)
    public Person get(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @GET
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.TEXT_HTML)
    @Counted(name = "performed_html", description = "How many it have been called.")
    @Timed(name = "checksTimer_html", description = "A measure of how long it takes to perform getting person.", unit = MetricUnits.MILLISECONDS)
    public Person getHTML(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @GET
    @Path("/{id}/pdf")
    @Produces("application/pdf")
    @Transactional
    @Counted(name = "performed_pdf", description = "How many it have been called.")
    @Timed(name = "checksTimer_pdf", description = "A measure of how long it takes to perform getting person.", unit = MetricUnits.MILLISECONDS)
    public Person getByPDF(@PathParam("id") Long id) {
        return Person.findById(id);
    }
}