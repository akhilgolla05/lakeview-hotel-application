package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.exception.RoleAlreadyExistException;
import com.projects.lakesidehotel.exception.UserAlreadyExistsException;
import com.projects.lakesidehotel.model.Role;
import com.projects.lakesidehotel.model.User;
import com.projects.lakesidehotel.repository.RoleRepository;
import com.projects.lakesidehotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;


    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.RemoveAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);

    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+" is already assign to this "+role.get().getName());
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());

        }
        return user.get();
    }

    @Override
    public Role RemoveAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUserFromRole();
        return roleRepository.save(role.get());
    }
}
