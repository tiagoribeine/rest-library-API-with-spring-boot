package com.github.tiagoribeine.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    public Author() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nationality, birthDate, biography);
    }
}
