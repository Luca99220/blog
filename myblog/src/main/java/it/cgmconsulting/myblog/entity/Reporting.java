package it.cgmconsulting.myblog.entity;

import it.cgmconsulting.myblog.entity.common.CreationUpdate;
import it.cgmconsulting.myblog.entity.enumeration.ReportingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Reporting extends CreationUpdate {

    @EmbeddedId
    private ReportingId reportingId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "reason"),
            @JoinColumn(name = "reason_start_date")})
    private Reason reason;

    @Enumerated(EnumType.STRING)
    private ReportingStatus status = ReportingStatus.NEW;

    @ManyToOne
    @JoinColumn(name ="reporter", nullable = false)
    private User reporter;

    public Reporting(ReportingId reportingId, Reason reason, User reporter){
        this.reportingId=reportingId;
        this.reason=reason;
        this.reporter = reporter;

    }
}
