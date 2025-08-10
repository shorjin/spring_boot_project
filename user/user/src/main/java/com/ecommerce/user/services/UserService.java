package com.ecommerce.user.services;

import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
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

//    把 前端发来的请求数据（UserRequest） ➜ 更新到已有的数据库实体对象（User）。
//    🧠 举个例子：
//用户用前端表单修改资料，点了“保存”按钮，传给后端一份 UserRequest：
//    你不能直接把这个 UserRequest 存进数据库，要先更新已有的 User 实体对象，所以：
//updateUserFromRequest(existingUser, userRequest);
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


//        把 数据库查到的 User 实体对象 ➜ 转换成 给前端返回的 UserResponse DTO。
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

