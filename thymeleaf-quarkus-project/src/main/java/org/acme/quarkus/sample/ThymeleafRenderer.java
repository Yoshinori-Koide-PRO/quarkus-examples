package org.acme.quarkus.sample;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.plugins.providers.html.Renderable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * ThymeleafRenderer
 */
@ApplicationScoped
public class ThymeleafRenderer {

    private TemplateEngine templateEngine = new TemplateEngine() {{
        addDialect(new Java8TimeDialect());
        setTemplateResolver(new ClassLoaderTemplateResolver());
    }};

    public Renderer view(String path) {
        return new Renderer(templateEngine, "META-INF/resources/"+path);
    }

    public static class Renderer implements Renderable {
        private TemplateEngine templateEngine;
        private String path;
        private Map<String, Object> variables;
        public Renderer(TemplateEngine templateEngine, String path) {
            this.templateEngine = templateEngine;
            this.path = path;
            this.variables = new HashMap<>();
        }
        
        public Renderer with(String key, Object variable) {
            this.variables.put(key, variable);
            return this;
        }

        @Override
        public void render(HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException, WebApplicationException {
            WebContext context = new WebContext(request, response, request.getServletContext());

            context.setVariables(variables);
            
            templateEngine.process(
                    path, context,
                    new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        }
    }
}