package br.com.machina.tapestryrestexample.services;

import java.util.List;
import java.util.Optional;

import br.com.machina.tapestryrestexample.rest.entities.User;

/**
 * Service which provide methods for dealing with {@link User} instances.
 */
public interface UserService {

    /**
     * Stores an user and returns a version of it with the id property set.
     */
    User save(User user);
    
    /**
     * Returns an user by its id.
     */
    Optional<User> get(Long id);
    
    /**
     * Returns all users.
     */
    List<User> getAll();
    
    /**
     * Returns a string containing a JSON representation of an user.
     */
    String toJsonString(User user);
    
    /**
     * Returns an {@link User} instance based on a JSON representation in a string.
     */
    User toObject(String json);
    
}
