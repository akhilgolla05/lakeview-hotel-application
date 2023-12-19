package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.exception.UserAlreadyExistsException;
import com.projects.lakesidehotel.model.Role;
import com.projects.lakesidehotel.model.User;
import com.projects.lakesidehotel.repository.RoleRepository;
import com.projects.lakesidehotel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class UserService implements iUserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public User registerUser(User user) {
        if(userRepository.existByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists.");
        }
        //encode the password, if user not exists
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);

    }

    @Override
    public User getUserById(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found."));
    }
}
