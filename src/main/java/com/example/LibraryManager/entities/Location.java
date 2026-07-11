package com.example.LibraryManager.entities;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "location")
@JsonIgnoreProperties({"books"})
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer floor;

    @OneToMany(mappedBy = "location")
    private List<Book> books;






}
