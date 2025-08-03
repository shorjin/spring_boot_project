package com.app.ecom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    private List<User> userList = new ArrayList<>();
//    private long nextId = 1L;



    public List<User> fetchAllUsers(){
        return userRepository.findAll();
    }

    public void addUser( User user) {
//        user.setId(nextId++);
        userRepository.save(user);

    }

    public Optional<User> fetchUser(long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

//    public boolean updateUser(long id, User updatedUser) {
//        return userList.stream()
//                .filter(user -> user.getId() == id)
//                .findFirst()
//                .map(existingUser ->{
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    return true;
//
//                }).orElse(false);
//        }

    public boolean updateUser(long id, User updatedUser) {
        return userRepository.findById(id)

                .map(existingUser ->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;

                }).orElse(false);

        }
    }

