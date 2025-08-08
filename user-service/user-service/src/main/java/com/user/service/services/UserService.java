package com.user.service.services;

import com.user.service.entitys.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);


}
