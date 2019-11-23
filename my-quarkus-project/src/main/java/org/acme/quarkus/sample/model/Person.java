package org.acme.quarkus.sample.model;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.json.bind.annotation.JsonbTransient;
import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Person extends PanacheEntity {
  public enum Status {
    Alive, DECEASED
  }
  
  public String name;
  @JsonFormat(pattern = "yyyy-MM-dd")
  public LocalDate birth;
  public Status status;

  @JsonbTransient
  @ManyToMany
  @JoinTable(name = "friends")
  public List<Person> friends;

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