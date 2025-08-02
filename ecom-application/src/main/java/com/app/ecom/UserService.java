package com.app.ecom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private long nextId = 1L;

    public List<User> fetchAllUsers(){
        return userList;
    }

    public void addUser( User user) {
        user.setId(nextId++);
        userList.add(user);

    }

    public Optional<User> fetchUser(long id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public void deleteUser(long id) {
        userList.removeIf(user -> user.getId() == id);
    }

    public boolean updateUser(long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(existingUser ->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;

                }).orElse(false);
        }
    }

