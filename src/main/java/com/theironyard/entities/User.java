package com.theironyard.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    @Transient //won't go into the database
    boolean author = false;

    @ManyToMany
    @JoinTable(name = "user_concerts",joinColumns={@JoinColumn(name="users_id")}, inverseJoinColumns={@JoinColumn(name="concerts_id")})
    List<Concert> concertList;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List getConcertList() {
        return concertList;
    }

    public void setConcertList(List concertList) {
        this.concertList = concertList;
    }
}
