package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jonathandavidblack on 6/23/16.
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    public User findByName(String name);
}
