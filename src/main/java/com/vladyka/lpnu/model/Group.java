package com.vladyka.lpnu.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @Column(nullable = false)
    private String abbr;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public Group setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAbbr() {
        return abbr;
    }

    public Group setAbbr(String abbr) {
        this.abbr = abbr;
        return this;
    }

    public Institute getInstitute() {
        return institute;
    }

    public Group setInstitute(Institute institute) {
        this.institute = institute;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Group setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }

}
