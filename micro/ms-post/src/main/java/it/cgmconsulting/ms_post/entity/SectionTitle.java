package it.cgmconsulting.ms_post.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SectionTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private byte id;

    @Column(nullable = false, length = 50, unique = true)
    private String sectionTitle;

    boolean visible = true;

    public SectionTitle(String sectionTitle){
        this.sectionTitle = sectionTitle;
    }
}
