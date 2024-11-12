package it.cgmconsulting.myblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment x la pk
    @EqualsAndHashCode.Include
    private short id;

    @Column(length = 50, nullable = false, unique = true)
    private String tagName; //sul db è colonna tag_name

    private boolean visible = true; //sul è DB bit 0false 1true


    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();


    //costruttore del tag name
    public Tag(String tagName){
        this.tagName = tagName;
    }
}
