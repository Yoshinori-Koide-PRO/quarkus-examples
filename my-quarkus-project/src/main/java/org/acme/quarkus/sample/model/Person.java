package org.acme.quarkus.sample.model;

import javax.persistence.Entity;
import java.time.LocalDate;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

public enum Status {
  Alive, DECEASED
}

@Entity
public class Person extends PanacheEntity {
  public String name;
  public LocalDate birth;
  public Status status;

  public static Person findByName(String name) {
    return find("name", name).firstResult();
  }

  public static List<Person> findAlive() {
    return list("status", Status.Alive);
  }

  public static void deleteStefs() {
    delete("name", "Stef");
  }
}