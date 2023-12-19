package com.projects.lakesidehotel.controller;

import com.projects.lakesidehotel.exception.RoleAlreadyExistException;
import com.projects.lakesidehotel.model.Role;
import com.projects.lakesidehotel.model.User;
import com.projects.lakesidehotel.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){

        return new ResponseEntity<List<Role>>(roleService.getRoles(), HttpStatus.FOUND);

    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createNewRole(@RequestBody Role theRole){

        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New Role Created Successfully!");
        }catch(RoleAlreadyExistException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){

        return roleService.RemoveAllUsersFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("userId") Long userId,
                                   @RequestParam("roleId") Long roleId){

        return roleService.removeUserFromRole(userId,roleId);


    }

    @PostMapping("/assign-user-to-role")
    public User assignRoleToUser(@RequestParam("userId") Long userId,
                                 @RequestParam("roleId") Long roleId){

        return roleService.assignRoleToUser(userId,roleId);

    }
}
