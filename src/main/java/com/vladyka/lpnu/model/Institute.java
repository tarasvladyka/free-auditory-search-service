package com.vladyka.lpnu.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "institute")
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @Column(nullable = false)
    private String abbr;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public Institute setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAbbr() {
        return abbr;
    }

    public Institute setAbbr(String abbr) {
        this.abbr = abbr;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Institute setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }
}