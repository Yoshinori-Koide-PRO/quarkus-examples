package org.acme.quarkus.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.metrics.annotation.Counted;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedChecks", description = "How many it have been hellowed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the say hellow.", unit = MetricUnits.MILLISECONDS)
    public String hello() {
        return "hello 1111\n";
    }
}