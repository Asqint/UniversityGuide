package com.grsu.guide.domain;


import javax.persistence.*;
import lombok.Data;
import com.sun.istack.NotNull;

@Data
@Entity
public class Page {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="head")
    private String head;

    @Column(name="text")
    private String text;

    @Column(name="audio")
    private String audio;

    @Column(name="video")
    private String video;

    public Page(long id, String head, String text, String audio, String video){
        this.id = id;
        this.head = head;
        this.text = text;
        this.audio = audio;
        this.video = video;
    }

    public Page() {}
}
