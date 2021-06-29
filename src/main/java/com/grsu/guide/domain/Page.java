package com.grsu.guide.domain;


import javax.persistence.*;
import lombok.Data;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Set;

@Data
@Table(name = "pages")
@Entity
public class Page {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name_page")
    private String namePage;

    @Column(name="parent_page_id")
    private Long parentPageId;

    @EqualsAndHashCode.Exclude
    @Column(name="element")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "page_id")
    private List<Element> elements;


    public Page(String namePage, List<Element> elements){
        this.namePage = namePage;
        this.elements = elements;
    }

    public Page() {}
}
