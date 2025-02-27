package it.cgmconsulting.myblog.entity;

import it.cgmconsulting.myblog.entity.common.CreationUpdate;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Post extends CreationUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String title;

    private String overview;

    @Column(nullable = false, length = 65535)//oppure TEXT-> lunghezza del post 64k
    private String content;

    @Column(length = 16)
    private String image;

    private LocalDate publicationDate; // se è a null oppure publicationDate.afterOrEqual(LocalDate.now()) significa che il post non è pubblicato

    private short totComments;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User userId;

    @ManyToMany
    @JoinTable(name = "post_tags",
               joinColumns = @JoinColumn(name = "post_id"),
               inverseJoinColumns =@JoinColumn(name = "tag_id") )
    @OrderBy("tagName ASC")//in modo che i tag vengano ordinati alfabeticamente
    private Set<Tag> tags = new HashSet<>();//abbiamo associato al post un set di tag set è una collezione non ordinata

    //metodi che servono per sincronizzare gli oggetti Post e Tag
    //qyando aggiungo o rimuovo un Tag ad un Post
    public void addTag(Tag tag){
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
        tag.getPosts().remove(this);
    }
    public Post(String title, String content, User userId, String overview){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.overview = overview;

    }
}
