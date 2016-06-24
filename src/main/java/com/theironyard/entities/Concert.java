package com.theironyard.entities;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
@Entity
@Table(name = "concerts")
public class Concert {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String bandName;
    @Column(nullable = false)
    String venue;
    @Column(nullable = false)
    LocalDateTime date;
    @Column(nullable = false)
    int rating;
    @Column
    String highlights;

    @ManyToOne
    User creator;

    @ManyToMany(mappedBy="concertList", cascade = CascadeType.ALL)
    List<User> userList;

    public Concert() {
    }


    public Concert(String bandName, String venue, LocalDateTime date, int rating, String highlights) {
        this.bandName = bandName;
        this.venue = venue;
        this.date = date;
        this.rating = rating;
        this.highlights = highlights;
    }

    public Concert(String bandName, String venue, LocalDateTime date, int rating, String highlights, User creator) {
        this.bandName = bandName;
        this.venue = venue;
        this.date = date;
        this.rating = rating;
        this.highlights = highlights;
        this.creator = creator;
    }

    public Concert(int id, String bandName, String venue, LocalDateTime date, int rating, String highlights) {
        this.id = id;
        this.bandName = bandName;
        this.venue = venue;
        this.date = date;
        this.rating = rating;
        this.highlights = highlights;
    }

    public Concert(String bandName, String venue, LocalDateTime date, int rating, String highlights, List<User> userList) {
        this.bandName = bandName;
        this.venue = venue;
        this.date = date;
        this.rating = rating;
        this.highlights = highlights;
        this.userList = userList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        rating = rating;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean containsUser(int userId) {
        for(User u : userList) {
            if(u.id == userId) {
                return true;
            }
        }
        return false;
    }
}
