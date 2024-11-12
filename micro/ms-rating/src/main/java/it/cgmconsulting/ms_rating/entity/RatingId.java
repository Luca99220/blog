package it.cgmconsulting.ms_rating.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RatingId implements Serializable {

    @EqualsAndHashCode.Include
    private int postId;
    @EqualsAndHashCode.Include
    private int userId;


}
