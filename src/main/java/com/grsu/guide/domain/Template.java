package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Template {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value",length = 2048)
    private String value;

    @Column(name = "user_id")
    private Long userId;

    public Template(String name,String value, Long userId){
        this.name = name;
        this.value = value;
        this.userId = userId;
    }

    public Template() {

    }
}
