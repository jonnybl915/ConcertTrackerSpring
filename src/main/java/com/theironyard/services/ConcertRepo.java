package com.theironyard.services;

import com.theironyard.entities.Concert;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
public interface ConcertRepo extends CrudRepository<Concert, Integer> {

}
