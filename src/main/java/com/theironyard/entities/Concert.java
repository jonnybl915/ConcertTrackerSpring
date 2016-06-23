package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
@Entity
@Table(name = "concerts")
public class Concert {
    @Id
    @GeneratedValue
    int id;

    String bandName;

}
