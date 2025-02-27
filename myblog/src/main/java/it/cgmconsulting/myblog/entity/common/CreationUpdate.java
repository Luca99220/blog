package it.cgmconsulting.myblog.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter@Setter
@MappedSuperclass //questi due attributi diventeranno due campi x quelli che estendono questa classe e diventeranno colonne della tabella che le hanno
public class CreationUpdate implements Serializable {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
