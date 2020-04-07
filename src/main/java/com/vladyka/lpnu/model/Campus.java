package com.vladyka.lpnu.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campus")
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public Campus setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Campus setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Campus setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }

}
