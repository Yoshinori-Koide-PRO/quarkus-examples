package org.acme.mongodb.panache.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Map;
import javax.json.bind.annotation.JsonbDateFormat;
import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection = "ThePerson")
public class Person extends PanacheMongoEntity {
    public enum Status {
        Alive, DECEASED
    }

    public String name;

    // will be persisted as a 'birth' field in MongoDB
    @BsonProperty("birth")
    @JsonbDateFormat("yyyy-MM-dd")
    public LocalDate birthDate;

    public Status status;

    public void setName(String name) {
        this.name = name;

        Annotation mongoEntity = this.getClass().<MongoEntity>getAnnotation(MongoEntity.class);

        changeAnnotationValue(mongoEntity, "collection", "ThePerson/" + name);
    }

    @SuppressWarnings("unchecked")
    public void changeAnnotationValue(Annotation annotation, String key, final String newValue) {

        try {
            Field field = Class.class.getDeclaredField("annotationData");
            field.setAccessible(true);
            Object annotationData = field.get(Person.class);
            Field annotationsField = annotationData.getClass().getDeclaredField("annotations");
            annotationsField.setAccessible(true);
            Map<Class<? extends Annotation>, Annotation> annotations =
                    (Map<Class<? extends Annotation>, Annotation>) annotationsField
                            .get(annotationData);
            Annotation newMongoEntity = new MongoEntity() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return annotation.annotationType();
                }

                @Override
                public String collection() {
                    return newValue;
                }

                @Override
                public String database() {
                    return "";
                }
            };

            annotations.put(MongoEntity.class, newMongoEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

