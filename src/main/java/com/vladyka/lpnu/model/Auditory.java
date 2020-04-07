package com.vladyka.lpnu.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditory")
public class Auditory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @Column(nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;

    @Column(nullable = false)
    private LocalDateTime createdOn;


    public Long getId() {
        return id;
    }

    public Auditory setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Auditory setNumber(String number) {
        this.number = number;
        return this;
    }

    public Campus getCampus() {
        return campus;
    }

    public Auditory setCampus(Campus campus) {
        this.campus = campus;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
