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

    @Column(name = "value",length = 2048)
    private String value;

    @Column(name="editor")
    private String editor;

    public Element(String value){
        this.value = value;
    }

    public Element() {
    }
}