package com.projects.lakesidehotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }


    //assign role to user
    public void assignRoleToUser(User user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    //remove user from roles
    public void removeUserFromRole(User user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUserFromRole(){
        if(this.getUsers() != null){
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);

        }
    }

    //get the name for a role
    public String getName(){
        return name != null ? name :"";
    }

}
