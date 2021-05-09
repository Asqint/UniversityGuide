package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Element {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "value",length = 2048)
    private String value;

    public Element(String type,String value){
        this.type = type;
        this.value = value;
    }

    public Element() {

    }
}