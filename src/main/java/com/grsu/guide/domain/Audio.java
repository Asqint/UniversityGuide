package com.grsu.guide.domain;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class Audio {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="audio")
    private String audio;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    private Page page;

    public Audio(long id,String audio, Page page){
        this.id = id;
        this.audio = audio;
        this.page = page;
    }

    public Audio() {}
}


