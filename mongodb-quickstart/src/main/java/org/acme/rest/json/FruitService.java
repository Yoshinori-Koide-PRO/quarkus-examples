package org.acme.rest.json;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.conversions.Bson;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class FruitService {

  @Inject
  MongoClient mongoClient;

  public List<Fruit> list() {
    List<Fruit> list = new ArrayList<>();
    MongoCursor<Fruit> cursor = getCollection().find().iterator();

    try {
      while (cursor.hasNext()) {
        list.add(cursor.next());
      }
    } finally {
      cursor.close();
    }
    return list;
  }

  public List<Fruit> findByPath(String path) {
    List<Fruit> list = new ArrayList<>();
    MongoCursor<Fruit> cursor =
        getCollection().find(new BasicDBObject("path", Pattern.compile(path))).iterator();

    try {
      while (cursor.hasNext()) {
        list.add(cursor.next());
      }
      System.out.println(String.format("coount::[%d]", list.size()));
    } finally {
      cursor.close();
    }
    return list;
  }

  public Fruit add(Fruit fruit) {
    getCollection(fruit.getName()).insertOne(fruit);
    return fruit;
  }

  private MongoCollection<Fruit> getCollection() {
    return mongoClient.getDatabase("fruit").getCollection("fruit/apple", Fruit.class);
  }

  private MongoCollection<Fruit> getCollection(String name) {
    return mongoClient.getDatabase("fruit").getCollection("fruit/" + name, Fruit.class);
  }
}
