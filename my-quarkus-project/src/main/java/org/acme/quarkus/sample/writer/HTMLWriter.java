package org.acme.quarkus.sample.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.acme.quarkus.sample.model.Person;

@Provider
@Produces(MediaType.TEXT_HTML)
public class HTMLWriter implements MessageBodyWriter<Person> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Person t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
                Writer writer = new PrintWriter(entityStream);
                writer.write("<html>");
                writer.write("<body>");
                writer.write("<h2>JAX-RS Message Body Writer Example</h2>");
                writer.write("<p>Id: " + t.id + "</p>");
                writer.write("<p>Name: " + t.name + "</p>");
                writer.write("</body>");
                writer.write("</html>");
        
                writer.flush();
                writer.close();
            }

}