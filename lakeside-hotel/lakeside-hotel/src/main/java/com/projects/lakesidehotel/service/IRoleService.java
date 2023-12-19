package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.model.Role;
import com.projects.lakesidehotel.model.User;

import java.util.List;

public interface IRoleService {

    List<Role> getRoles();

    Role createRole(Role theRole);

    void deleteRole(Long id);

    Role findRoleByName(String name);

    //we considered email as id
    User removeUserFromRole(Long userId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role RemoveAllUsersFromRole(Long roleId);

}
