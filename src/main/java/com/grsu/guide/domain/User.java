package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@Table(name = "usr")
@Entity
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;


    public User(String name,String password, String role){
        this.userName = name;
        this.password = password;
        this.role = role;
    }

    public User() {}

}