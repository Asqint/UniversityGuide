package com.grsu.guide.domain;


import javax.persistence.*;
import lombok.Data;
import com.sun.istack.NotNull;

import java.util.Set;

@Data
@Entity
public class Page {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="namePage")
    private String namePage;

    @Column(name="head")
    private String head;

    @Column(name="texts")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Text> texts;

    @Column(name="audios")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Audio> audios;

    @Column(name="videos")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Video> videos;

    public Page(String head, Set<Text> texts, Set<Audio> audios, Set<Video> videos, String namePage){
        this.head = head;
        this.texts = texts;
        this.audios = audios;
        this.videos = videos;
        this.namePage = namePage;
    }

    public Page() {}
}
