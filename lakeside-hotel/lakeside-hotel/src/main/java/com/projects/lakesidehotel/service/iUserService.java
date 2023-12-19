package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.model.User;

import java.util.List;

public interface iUserService {

    User registerUser(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUserById(String email);

}
