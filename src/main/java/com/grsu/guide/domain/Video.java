package com.grsu.guide.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@Entity
public class Video {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="video")
    private String video;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Page page;

    public Video(long id,String video, Page page){
        this.id = id;
        this.video = video;
        this.page = page;
    }

    public Video() {}
}

