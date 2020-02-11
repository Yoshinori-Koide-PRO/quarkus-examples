package org.acme.mongodb.panache.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection="ThePerson")
public class Friend extends PanacheMongoEntity {
    public String name;

    public Person friendWith;

}