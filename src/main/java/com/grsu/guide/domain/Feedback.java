package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Feedback {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="message")
    private String message;

    public Feedback(String name,String email,String message){
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public Feedback() {}

}
