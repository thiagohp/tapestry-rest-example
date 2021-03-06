package br.com.machina.tapestryrestexample.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.machina.tapestryrestexample.entities.User;

/**
 * Memory-only {@link UserService} implementation.
 */
public class UserServiceImpl implements UserService {

    final private List<User> users = new ArrayList<User>();
    
    final private ObjectMapper objectMapper;
    
    public UserServiceImpl() {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"));        
    }
    
    public User save(User user) {
        user.setId((long) users.size());
        users.add(user);
        return user;
    }

    public Optional<User> get(Long id) {
        return users.stream().filter(u -> id.equals(u.getId())).findFirst();
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public String toJsonString(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User toObject(String json) {
        try {
            return objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
