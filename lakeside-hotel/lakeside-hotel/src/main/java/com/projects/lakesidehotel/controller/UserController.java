package com.projects.lakesidehotel.controller;

import com.projects.lakesidehotel.model.User;
import com.projects.lakesidehotel.service.iUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final iUserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            System.out.println("************************************");
            System.out.println(email);
            System.out.println("****************************************");
            User theUser = userService.getUser(email);
            System.out.println("-------------------------------------");
            System.out.println(theUser);
            System.out.println("-------------------------------------");
//            return ResponseEntity.ok(theUser);
            return new ResponseEntity<User>(theUser, HttpStatus.FOUND);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or (hasRole('ROLE_ADMIN') and #email == principal.username)")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){

        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User Deleted Successfully");
        }catch(UsernameNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting User");
        }
    }

}
