package com.app.ecom.service;
import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    private List<User> userList = new ArrayList<>();
//    private long nextId = 1L;



    public List<UserResponse> fetchAllUsers(){
        List<User> userList = userRepository.findAll();
        return  userList.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
//        user.setId(nextId++);
        User user = new User();
        updateUserFromRequest (user,userRequest);
        userRepository.save(user);

    }
    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }
    }


    public Optional<UserResponse> fetchUser(long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
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

    public boolean updateUser(long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)

                .map(existingUser ->{
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;

                }).orElse(false);

        }
     private UserResponse mapToUserResponse (User user)   {
        UserResponse response = new UserResponse();
         response.setId(String.valueOf(user.getId()));
         response.setFirstName(user.getFirstName());
         response.setLastName(user.getLastName());
         response.setEmail(user.getEmail());
         response.setPhone(user.getPhone());
         response.setRole(user.getRole());

         if (user.getAddress() != null) {
             AddressDTO addressDTO = new AddressDTO();
             addressDTO.setStreet(user.getAddress().getStreet());
             addressDTO.setCity(user.getAddress().getCity());
             addressDTO.setState(user.getAddress().getState());
             addressDTO.setCountry(user.getAddress().getCountry());
             addressDTO.setZipcode(user.getAddress().getZipcode());
             response.setAddress(addressDTO);
         }
         return response;
     }
    }

