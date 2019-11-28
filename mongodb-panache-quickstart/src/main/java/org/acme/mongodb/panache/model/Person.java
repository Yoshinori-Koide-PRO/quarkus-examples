package org.acme.mongodb.panache.model;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection="ThePerson")
public class Person extends PanacheMongoEntity {
    public enum Status {
        Alive, DECEASED
    }
    public String name;

    // will be persisted as a 'birth' field in MongoDB
    @BsonProperty("birth")
    public LocalDate birthDate;

    public Status status;
}