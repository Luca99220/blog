package it.cgmconsulting.myblog.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reason {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ReasonId reasonId;

    private LocalDate endDate;

    private int severity;

    public Reason(ReasonId reasonId, int severity){
        this.reasonId = reasonId;
        this.severity = severity;
    }
}
