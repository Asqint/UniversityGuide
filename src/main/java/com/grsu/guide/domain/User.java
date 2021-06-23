package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

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

    @Column(name="email")
    private String email;

    @Column(name="message")
    private String message;

    public User(String user_name,String email,String message){
        this.userName = user_name;
        this.email = email;
        this.message = message;
    }

    public User() {}
}